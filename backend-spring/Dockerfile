FROM alpine:latest

RUN apk update && apk add openjdk17

COPY . /backend-spring
WORKDIR /backend-spring
RUN chmod 755 ./fastbuild.sh

ENTRYPOINT ["sh", "fastbuild.sh"]
