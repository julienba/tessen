(ns com.simple.tessen.events
  (:require [clojure.tools.logging :as log]
            [cheshire.core :as json]
            [clj-http.client :as http]))

(defn all
  "Returns all events."
  [host]
  (let [url (str host "/events")]
    (http/get url)))

(defn for-client
  "Returns all events for the Sensu client CLIENT-NAME, filtering additionally
   for optional check name CHECK."
  [host client-name & {:keys [check] :or {check ""}}]
  (let [client (name client-name)
        check-name (name check)
        path (str "/events/" client "/" check-name)
        url (str host path)]
    (http/get url)))

(defn resolve-event
  "Resolves the Event categorized by the host name CLIENT-NAME and with check
   name CHECK-NAME."
  [host client-name check-name]
  (let [url (str host "/resolve")
        payload (json/generate-string {:client client-name
                                       :check check-name})]
    (http/post url {:body payload :content-type :json})))

(defn dispatch
  "Dispatches the keyword CMD to be run as an associated function call, with
   additional arguments ARGS."
  [host cmd & args]
  (case (name cmd)
    "all" (apply all host args)
    "for-client" (apply for-client host args)
    "resolve" (apply resolve-event host args)
    (log/warnf "Unknown command `%s'" cmd)))
