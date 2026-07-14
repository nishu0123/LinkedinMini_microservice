#!/bin/bash

if [ ! -f ".env" ]; then
    echo ".env file not found!"
    exit 1
fi

if [ $# -ne 1 ]; then
    echo "Usage: ./build-service.sh <service-directory>"
    exit 1
fi

set -a
source .env
set +a

cd "$1" || exit 1

mvn clean install