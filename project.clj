(def VERSION (.trim (slurp "VERSION")))

(defproject org.clojars.jakedavis/tessen VERSION
  :main tessen.core
  :description "Sensu API client in Clojure."
  :url "http://github.com/jakedavis/tessen"
  :license "Apache License, Version 2.0"
  :repl-options {:init-ns tessen.core}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [cheshire "5.4.0"]
                 [clj-http "1.0.1"]]
  :min-lein-version "2.0.0"
  :global-vars {*warn-on-reflection* true}
  :codox {:output-dir "target/doc"
          :src-dir-url "https://github.com/jakedavis/tessen"}
  :profiles {:dev {:plugins [[codox "0.8.10"]
                             [lein-cloverage "1.0.2"]]}})
