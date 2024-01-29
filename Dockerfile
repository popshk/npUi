FROM alpine:3.18

RUN apk add openjdk17

COPY target/npUI-0.0.1-SNAPSHOT.jar /app.jar

COPY src/main/resources/static /src/main/resources/static

ENTRYPOINT ["java", "-jar", "/app.jar"]
