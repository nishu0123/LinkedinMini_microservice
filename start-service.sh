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

# Check service name
if [ $# -ne 1 ]; then
    echo "Usage: ./start-service.sh <service-directory>"
    echo ""
    echo "Available services:"
    echo "  UserService"
    echo "  PostService"
    echo "  api-gateway"
    echo "  NotificationService"
    echo "  ConnectionService"
    echo "  UploaderService"
    echo "  discovery-service"
    exit 1
fi

# Validate directory
if [ ! -d "$1" ]; then
    echo "❌ Service directory '$1' not found."
    exit 1
fi

# Load environment variables
set -a
source .env
set +a

# Navigate to service
cd "$1" || exit 1

echo "======================================"
echo "Starting $1..."
echo "======================================"

mvn spring-boot:run -Dspring-boot.run.profiles=local