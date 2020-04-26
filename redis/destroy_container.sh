#!/bin/bash

. stdout_helper.sh

startAction remove\ running\ container
docker container stop color-schema
docker rm color-schema
docker rmi color-schema:1.0.0
endAction remove\ running\ container

docker ps -a
