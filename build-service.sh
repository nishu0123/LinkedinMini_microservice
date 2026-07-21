#!/bin/bash

# Directory where this script is located
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "$ROOT_DIR" || exit 1

# Ensure the script is run from the project root
if [ ! -f ".env" ]; then
    echo "❌ .env file not found!"
    echo "Please run this script from the LinkedinMini_microservice directory."
    exit 1
fi

# Validate arguments
if [ $# -lt 1 ] || [ $# -gt 2 ]; then
    echo "Usage: ./build-service.sh <service-directory> [profile]"
    echo ""
    echo "Examples:"
    echo "  ./build-service.sh discovery-service"
    echo "  ./build-service.sh discovery-service local"
    echo "  ./build-service.sh discovery-service docker"
    echo "  ./build-service.sh discovery-service prod"
    exit 1
fi

SERVICE=$1
PROFILE=${2:-local}

# Validate service directory
if [ ! -d "$SERVICE" ]; then
    echo "❌ Service directory '$SERVICE' not found."
    exit 1
fi

echo "======================================"
echo "Building Service : $SERVICE"
echo "Profile          : $PROFILE"
echo "======================================"

# Load environment variables
set -a
source .env
export SPRING_PROFILES_ACTIVE="$PROFILE"
set +a

# Navigate to service directory
cd "$SERVICE" || exit 1

# Build project
mvn clean install