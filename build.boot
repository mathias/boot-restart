(set-env!
 :dependencies '[[adzerk/bootlaces "0.1.9" :scope "test"]
                 [me.raynes/conch "0.8.0"]])

(require '[adzerk.bootlaces :refer :all])

(def +version+ "0.0.1")
(bootlaces! +version+)

(task-options!
 pom  {:project     'mathias/boot-run-continuously
       :version     +version+
       :description "Run a shell command continuously and restart it on file change."
       :url         "https://github.com/mathias/boot-run-continuously"
       :scm         {:url "https://github.com/mathias/boot-run-continously"}
       :license     {"name" "Eclipse Public License"
                     "url"  "http://www.eclipse.org/legal/epl-v10.html"}})
