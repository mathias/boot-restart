(ns mathias.boot-restart
  {:boot/export-tasks true}
  (:require [boot.core :as core :refer [deftask]]
            [boot.util :as util]
            [me.raynes.conch.low-level :as conch]))

(defn shell [command]
  (let [args (remove nil? (clojure.string/split command #" "))]
    (assert (every? string? args))
    (let [process (apply conch/proc args)]
      (future (conch/stream-to-out process :out))
      (future (conch/stream-to process :err (System/err)))
      process)))

(defn kill [process]
  (when-not (nil? process)
    (conch/destroy process)))

(defn exit-code [process]
  (future (conch/exit-code process)))

(deftask restart
  "Continuously run a shell command, and restart it when files change"
  [c command COMMAND str "Command line to run. Required."]
  (let [last-fileset (atom nil)
        process      (atom (shell command))]
    (core/with-pre-wrap fileset
      (let [last-fileset-val @last-fileset
            files-changed? (core/fileset-diff last-fileset-val fileset)
            exit         (exit-code @process)]

        (reset! last-fileset fileset)

        (when (realized? exit)
          (if (= 0 @exit)
            (util/warn "Process exited normally, restarting.")
            (util/fail "Process crashed, restarting."))
          (reset! process (shell command)))

        (when files-changed?
         (kill @process)
         (reset! process (shell command)))

        fileset))))
