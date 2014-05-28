(ns com.simple.tessen.events
  (:require [clj-http.client :as http]))

(defn all
  "Returns all events."
  [host]
  (http/get (str host "/events")))

(defn for-client
  "Returns all events for the Sensu client CLIENT, filtering additionally
   for optional check name CHECK."
  [host client & {:keys [check]}]
  (let [path (str client "/" check)
        url (str host path)] 
    (http/get url)))

(defn resolve-event
  "Resolves the Event categorized by the host name CLIENT and with check name
   CHECK."
  [host client check]
  (let [url (str host "/resolve")
        payload (json/generate-string {:client client :check check})]
    (http/post url {:body payload :content-type :json})))

(defn dispatch
  "Dispatches the keyword CMD to be run as an associated function call, with
   additional arguments ARGS."
  [host cmd & args]
  (case cmd
    "all" (apply all host args)
    "for-client" (apply for-client host args)
    "resolve" (apply resolve-event host args)
    :default (println (format "Unknown command `%s'" cmd))))
