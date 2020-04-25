FROM redis:latest

RUN mkdir /workspace
COPY seed.redis /workspace
COPY init.sh /workspace

CMD ["sh", "init.sh"]