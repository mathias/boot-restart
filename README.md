# boot-restart

Boot task to continuously run a shell command, and restart it when files change. Good for running non-Clojure servers and the like when your files are compiled.

[![Clojars Project](http://clojars.org/mathias/boot-restart/latest-version.svg)](http://clojars.org/mathias/boot-restart)

## Usage

Include the project:

```clojure
;; in build.boot
(set-env!
  :dependencies '[mathias/boot-restart "0.0.2"])
```

### Terminal

boot-restart is meant to be used with your other boot tasks. For example:

```
boot watch compile restart --command "node app.js"
```

### build.boot file in your project

In your `build.boot` you could call it like this:

```clojure
(deftask run
  []
  (comp (watch) (restart :command "node app.js")))
```

## Options

See the [boot project](https://github.com/boot-clj/boot) for more information
on how to use these.

```clojure
[c command COMMAND str "Command line to run continuously. Required."]
```

## License

Copyright Matt Gauger 2015.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
