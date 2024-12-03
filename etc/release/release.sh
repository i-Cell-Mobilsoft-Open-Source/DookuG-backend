#!/usr/bin/env bash
set -ex

# import .env file (resolves inner values as well)
. etc/release/.env

echo "Starting release from $RELEASED_VERSION"

###############################################################################
# tag + push images
###############################################################################
docker push ${DDOCKER_SERVICE_QUARKUS_SAMPLE_REST_SERVICE}:${RELEASED_VERSION}

echo "Release from $RELEASED_VERSION ended"
