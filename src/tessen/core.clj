(ns tessen.core
  (:require [tessen.aggregates :as aggregates]
            [tessen.checks :as checks]
            [tessen.clients :as clients]
            [tessen.events :as events]
            [tessen.stashes :as stashes]
            [clojure.tools.logging :as log]
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
  (case (name cmd)
    "aggregates" (apply aggregates/dispatch host args)
    "checks" (apply checks/dispatch host args)
    "clients" (apply clients/dispatch host args)
    "events" (apply events/dispatch host args)
    "health" (apply health host args)
    "info" (apply info host args)
    "stashes" (apply stashes/dispatch host args)
    (log/warnf "Unknown command `%s'" cmd)))
