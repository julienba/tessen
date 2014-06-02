(ns com.simple.tessen.aggregates
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

(defn all
  "Returns all aggregates."
  [host & {:keys [offset limit]}]
  (let [url (str host "/aggregates")
        params (json/generate-string {"offset" offset
                                      "limit" limit})]
    (http/get url {:query-params params})))

(defn by-name
  "Returns the aggregate given by AGGREGATE-NAME, with optional parameter AGE,
   which confusingly affects 'The number of seconds from now to get
   aggregates.'"
  [host aggregate-name & {:keys [age]}]
  (let [url (str host "/aggregates/" aggregate-name)
        params (json/generate-string {"age" age})]
    (http/get url {:query-params params})))

(defn delete
  "Deletes the aggregate given by AGGREGATE-NAME."
  [host aggregate-name]
  (let [url (str host "/aggregates/" aggregate-name)]
    (http/delete url)))

;; TODO: /aggregates/:check_name/:check_issued endpoint

(defn dispatch
  "Dispatches the keyword CMD to be run as an associated function call, with
   additional arguments ARGS."
  [host cmd & args]
  (case cmd
    "all" (apply all host args)
    "by-name" (apply by-name host args)
    "delete" (apply delete host args)
    :default (println (format "Unknown command `%s'" cmd))))
