#!/bin/bash

CONTAINER_NAME="yogaveda_main_container"

sudo yum update -y   # For Amazon Linux
sudo yum install docker -y # install docker for Amazon Linux

sudo service docker start # start the docker service
sudo usermod -aG docker $USER # add the current user to the docker group
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER --password-stdin # login to docker hub
docker pull raulthakur/yogaveda-server:latest # pull the latest image
docker run -d -p 80:8080 --name $CONTAINER_NAME --env-file /tmp/prod.env raulthakur/yogaveda-server:latest # run the container