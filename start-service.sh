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

# Check arguments
if [ $# -lt 1 ] || [ $# -gt 2 ]; then
    echo "Usage: ./start-service.sh <service-directory> [profile]"
    echo ""
    echo "Available services:"
    echo "  UserService"
    echo "  PostService"
    echo "  api-gateway"
    echo "  NotificationService"
    echo "  ConnectionService"
    echo "  UploaderService"
    echo "  discovery-service"
    echo ""
    echo "Examples:"
    echo "  ./start-service.sh discovery-service"
    echo "  ./start-service.sh discovery-service local"
    echo "  ./start-service.sh discovery-service docker"
    exit 1
fi

SERVICE=$1
PROFILE=${2:-local}

# Validate directory
if [ ! -d "$SERVICE" ]; then
    echo "❌ Service directory '$SERVICE' not found."
    exit 1
fi

# Load environment variables
set -a
source .env
export SPRING_PROFILES_ACTIVE="$PROFILE"
set +a

# Navigate to service
cd "$SERVICE" || exit 1

echo "======================================"
echo "Starting Service : $SERVICE"
echo "Profile          : $PROFILE"
echo "======================================"

mvn spring-boot:run -Dspring-boot.run.profiles="$PROFILE"