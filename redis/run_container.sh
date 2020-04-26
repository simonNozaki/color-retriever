#!/bin/bash

. stdout_helper.sh

# build the container
startAction Boot\ Container
docker build -t color-schema:1.0.0 .
docker run --name color-schema -d -p 6379:6379 color-schema:1.0.0
endAction Boot Container

startAction Print\ status
docker ps
docker logs color-schema
endAction Print status