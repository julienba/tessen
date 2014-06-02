(ns com.simple.tessen.stashes
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn all
  "Returns all stashes."
  [host & {:keys [offset limit]}]
  (let [url (str host "/stashes")
        params {"offset" offset, "limit" limit}]
    (http/get url {:query-params params})))

(defn create
  "Creates a stash at path PATH with content map CONTENT. Optional expiry after
   EXPIRATION seconds."
  [host path content & {:keys [expiration]
                        :or [expiration -1]}]
  (let [url (str host "/stashes")
        payload (json/generate-string {"path" path
                                       "content" content
                                       "expire" expiration})]
    (http/post url {:body payload :content-type :json})))

(defn by-name
  "Retrieves the stash given by name STASH."
  [host stash]
  (let [url (str host "/stashes/" stash)]
    (http/get url)))

(defn delete
  "Deletes the stash given by name STASH."
  [host stash]
  (let [url (str host "/stashes/" stash)]
    (http/delete url)))

(defn dispatch
  "Dispatches the keyword CMD to be run as an associated function call, with
   additional arguments ARGS."
  [host cmd & args]
  (case cmd
    "all" (apply all host args)
    "by-name" (apply by-name host args)
    "create" (apply create host args)
    "delete" (apply delete host args)
    :default (println (format "Unknown command `%s'" cmd))))
