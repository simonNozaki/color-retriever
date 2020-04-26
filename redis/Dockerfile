FROM redis:latest

RUN mkdir /workspace
COPY seed.redis /workspace
COPY init.sh /workspace

RUN chmod +x /workspace/init.sh
RUN chmod +x /workspace/seed.redis

# CMD ["redis-cli < /workspace/seed.redis"]
