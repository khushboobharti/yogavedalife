#!/bin/bash

# Define the image and container names
IMAGE_NAME="raulthakur/yogaveda-server"
CONTAINER_NAME="yogaveda_main_container"
ENV_FILE="/tmp/prod.env"

# Step 1: Pull the latest image from Docker Hub
echo "Pulling the latest image: $IMAGE_NAME..."
docker pull $IMAGE_NAME

# Step 2: Stop the existing container if it's running
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Stopping the existing container: $CONTAINER_NAME..."
    docker stop $CONTAINER_NAME
fi

# Step 3: Remove the existing container
if [ "$(docker ps -a -q -f name=$CONTAINER_NAME)" ]; then
    echo "Removing the existing container: $CONTAINER_NAME..."
    docker rm $CONTAINER_NAME
fi

# Step 4: Run a new container with the updated image
echo "Starting a new container with the updated image..."
docker run -d -p 80:8080 --name $CONTAINER_NAME --env-file $ENV_FILE $IMAGE_NAME

docker run -d -p 80:8080 --name yogaveda_main_container --env-file /tmp/prod.env raulthakur/yogaveda-server