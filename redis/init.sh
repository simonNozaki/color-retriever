#!/bin/bash

redis-server --daemonize yes && sleep 1

redis-cli < /workspace/seed.redis

redis-cli shutdown

redis-server