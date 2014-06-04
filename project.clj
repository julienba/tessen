(def VERSION (.trim (slurp "VERSION")))

(defproject tessen VERSION
  :description "Sensu API client in Clojure."
  :url "http://github.com/jakedavis/tessen"
  :license "Apache License, Version 2.0"
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.clojure/algo.generic "0.1.2"]
                 [cheshire "5.3.1"]
                 [clj-http "0.9.1"]]
  :main com.simple.tessen.core
  :min-lein-version "2.0.0"
  :global-vars {*warn-on-reflection* true})
