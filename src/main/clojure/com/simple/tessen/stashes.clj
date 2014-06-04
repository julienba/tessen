(ns com.simple.tessen.stashes
  (:require [clojure.tools.logging :as log]
            [clj-http.client :as http]
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
  "Retrieves the stash given by name STASH-NAME."
  [host stash-name]
  (let [stash (name stash-name)
        url (str host "/stashes/" stash)]
    (http/get url)))

(defn delete
  "Deletes the stash given by name STASH-NAME."
  [host stash-name]
  (let [stash (name stash-name)
        url (str host "/stashes/" stash)]
    (http/delete url)))

(defn dispatch
  "Dispatches the keyword CMD to be run as an associated function call, with
   additional arguments ARGS."
  [host cmd & args]
  (case (name cmd)
    "all" (apply all host args)
    "by-name" (apply by-name host args)
    "create" (apply create host args)
    "delete" (apply delete host args)
    (log/warnf "Unknown command `%s'" cmd)))
