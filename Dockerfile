FROM alpine:latest
RUN apk add openjdk17
COPY /target/movie-application-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
