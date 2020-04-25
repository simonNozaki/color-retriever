#!/bin/bash

# build the container
docker build -t color-schema:1.0.0 .
docker run --name color-schema -d -p 6379:6379 color-schema:1.0.0

docker ps

docker logs color-schema