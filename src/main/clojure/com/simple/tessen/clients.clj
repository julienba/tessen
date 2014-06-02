(ns com.simple.tessen.clients
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

(defn all
  "Returns all clients."
  [host & {:keys [offset limit]}]
  (let [url (str host "/clients")]
    (http/get url {:query-params {"offset" offset 
                                  "limit" limit}})))

(defn by-name
  "Returns the client given by name CLIENT-NAME."
  [host client-name]
  (let [url (str host "/clients/" client-name)]
    (http/get url)))

(defn history
  "Returns the history of the Sensu client given by CLIENT-NAME with optional
   additional filter by check CHECK-NAME."
  [host client-name]
  (let [url (str host "/clients/" client-name "/history")]
   (http/get url)))

(defn delete
  "Deletes the Sensu client given by CLIENT-NAME"
  [host client-name]
  (let [url (str host "/clients/" client-name)]
    (http/delete url)))

(defn dispatch
  "Dispatches the keyword CMD to be run as an associated function call, with
   additional arguments ARGS."
  [host cmd & args]
  (case cmd
    "all" (apply all host args)
    "by-name" (apply by-name host args)
    "history" (apply history host args)
    "delete" (apply delete host args)
    (println (format "Unknown subcommand `%s'" cmd))))
