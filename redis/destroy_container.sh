#!/bin/bash

docker container stop color-schema
docker rm color-schema
docker rmi color-schema:1.0.0

docker ps -a
