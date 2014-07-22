# Tessen
Sensu API client written in Clojure.

## Installaton
`[tessen "0.1.0"]`

## Usage
Tessen namespaces each individual Sensu API endpoint
([docs](http://sensuapp.org/docs/0.12/api)) as a separate namespace, outside of
the `/info` and `/health` endpoints. In addition, it comes with a core
namespace that offers the ability to access each individual namespace as a
"subcommand":

``` clojure
(require '(com.simple.tessen.core :as tessen))
(def sensu-api (partial tessen/dispatch "http://sensu.example.com:4567"))

;; General
(sensu-api :info)
(sensu-api :health :consumers 2 :messages 10)

;; Aggregates
(sensu-api :aggregates :all)
(sensu-api :aggregates :by-name :my-aggregate)
(sensu-api :aggregates :delete :my-aggregate)

;; Checks
(sensu-api :checks :all)
(sensu-api :checks :by-name :my-check)
(sensu-api :checks :request [:my-client, :my-second-client] :my-check)

;; Clients
(sensu-api :clients :all)
(sensu-api :clients :by-name :my-client)
(sensu-api :clients :history :my-client)
(sensu-api :clients :delete :my-client)

;; Events
(sensu-api :events :all)
(sensu-api :events :for-client :my-client)
(sensu-api :events :resolve :my-client :my-check)

;; Stashes
(sensu-api :stashes :create {"hello" "goodbye"})
(sensu-api :stashes :by-name :my-stash)
(sensu-api :stashes :delete :my-stash)
```

Strings can be used in place of keywords.

## Author/License
Jake Davis <jake.davis5989@gmail.com>

Apache License, Version 2.0
