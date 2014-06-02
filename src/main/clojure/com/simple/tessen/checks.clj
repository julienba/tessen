(ns com.simple.tessen.checks
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

(defn all
  "Returns all checks."
  [host]
  (let [url (str host "/checks")]
    (http/get url)))

(defn by-name
  "Returns the check given by CHECK-NAME."
  [host check-name]
  (let [url (str host "/checks/" check-name)]
    (http/get url)))

(defn request
  "Issues a check request to be run by SUBSCRIBERS with name CHECK."
  [host subscribers check]
  (let [url (str host "/request")
        payload (json/generate-string {:check check
                                       :subscribers subscribers})]
    (http/post url {:body payload :content-type :json})))

(defn dispatch
  "Dispatches the keyword CMD to be run as an associated function call, with
   additional arguments ARGS."
  [host cmd & args]
  (case cmd
    "all" (apply all host args)
    "by-name" (apply by-name host args)
    "request" (apply request host args)
    (println (format "Unknown subcommand `%s'" cmd))))
