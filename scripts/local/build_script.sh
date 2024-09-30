#!/bin/sh

# Get the current directory
current_directory=$(pwd)
echo "Current folder: $current_directory"

# check pwd execute command if site directory
if [ -d "site" ]; then
    echo "site directory exists."
    # cd site
    # kobweb export --notty
else
    echo "site directory does not exist."
fi
#cd ..

#current_directory=$(pwd)
#echo "Current folder: $current_directory"

docker build . -f Dockerfile -t raulthakur/yogaveda-server:latest

docker push raulthakur/yogaveda-server:latest
