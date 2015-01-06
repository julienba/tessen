test:
	lein do clean, test

build:
	lein deploy clojars
