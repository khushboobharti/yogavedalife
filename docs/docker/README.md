## Multiple Docker Files
The 2 different docker files are created based on where the build for docker image is happening

## List of useful commands

## There are 2 tagging formats
Tagging for dockerhub - raulthakur/yogaveda-server:latest
Tagging for local daemon - yogaveda-server:latest

Always use dockerhub based tagging - check tagging formats for custom hosted docker container registry

### Build File 
docker build . -f Dockerfile -t raulthakur/yogaveda-server:latest

## Tag build
docker tag <image-id> raulthakur/yogaveda-server:latest

### Run image exposing and mapping the internal port (system level) to external port (container level)
docker run -d -p 80:8080 --env-file prod.env docker.io/library/yogaveda-server:latest

### Check logs os a running container
docker logs 9579b256bbeb780caeb7331e540a58cc7bedd7e4d7977f53f26291077a5bf029

## Login to docker hub
docker login

### Push to DockerHub
docker push raulthakur/yogaveda-server:yogaveda-server:latest
or
docker push raulthakur/yogaveda-server:latest

### Docker status
sudo systemctl status docker
or
ps aux | grep docker

# Understanding Docker container
A docker container was never meant to be restarted
https://stackoverflow.com/questions/28654076/docker-container-wont-start-because-an-existing-pid-file#answer-41295226

So do not worry about handling the KILL_SIGNAL and exiting the server.
Even on Kubernetes if a server crashes, a new container is created in the existing server's place.

## Check running docker containers
docker ps -a

## Remove a Docker container
docker rm -f <container-id>
