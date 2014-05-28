(ns com.simple.tessen.core
  (:require [com.simple.tessen.aggregates :as aggregates]
            [com.simple.tessen.checks :as checks]
            [com.simple.tessen.clients :as clients]
            [com.simple.tessen.events :as events]
            [com.simple.tessen.stashes :as stashes]
            [clj-http.client :as http]
            [cheshire.core :as json]))

(defn health
  "Queries /health endpoint, which returns a plain 202 or 503 depending on the
   health of the Sensu server. Accepts number of RabbitMQ consumers CONSUMERS 
   and RabbitMQ messages MESSAGES to determine the status to return."
  [host consumers messages]
  (let [url (str host "/health")]
    (http/get url {:query-params {"consumers" consumers 
                                  "messages" messages}})))

(defn info
  "Gets generic information about the Sensu server."
  [host]
  (let [url (str host "/info")]
    (http/get url)))

(defn dispatch
  "Dispatches the argument CMD to the correct sub-command for further
   dispatch, with additional arguments ARGS. Hits address HOST."
  [host cmd & args]
  (case cmd 
    "aggregates" (apply aggregates/dispatch host args)
    "checks" (apply checks/dispatch host args)
    "clients" (apply clients/dispatch host args)
    "events" (apply events/dispatch host args)
    "health" (apply health host args)
    "info" (apply info host args)
    "stashes" (apply stashes/dispatch host args)
    (println (format "Unknown command `%s'" cmd))))
