#!/bin/bash

yum update -y
yum install httpd -y
echo "Hello" > /var/www/html/index.html
service httpd restart

sudo yum update -y   # For Amazon Linux
sudo apt-get update # For Linux
sudo yum install docker -y # For Amazon Linux new
sudo systemctl start docker # for Amazon Linux
sudo usermod -aG docker $USER

#sudo amazon-linux-extras install docker # For Amazon Linux
#sudo apt-get install -y docker.io   # For Ubuntu


# sudo service docker start

sudo usermod -aG docker $USER
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin # Docker Hub login
docker pull raulthakur/yogaveda-server:latest
docker run -d -p 80:8080 raulthakur/yogaveda-server:latest