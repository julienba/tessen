(ns com.simple.tessen.events
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

(defn all
  "Returns all events."
  [host]
  (http/get (str host "/events")))

(defn for-client
  "Returns all events for the Sensu client CLIENT, filtering additionally
   for optional check name CHECK."
  [& {:keys [host port client check]
      :or {port 4567 check "/"}}]
  (let [url (str host ":" port "/events/" client check)] 
    (http/get url)))

(defn resolve-event
  "Resolves the Event categorized by the host name CLIENT and with check name
   CHECK."
  [host client check]
  (http/get ))
