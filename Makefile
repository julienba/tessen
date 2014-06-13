VERSION     := $(shell cat VERSION)
RELEASE_TAG := v$(VERSION)

build:
	-git tag -f $(RELEASE_TAG)
	-git push origin tag $(RELEASE_TAG)
	LEIN_SNAPSHOTS_IN_RELEASE=true lein do test, jar, deploy
