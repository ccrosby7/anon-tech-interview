FROM openjdk:11-jdk-slim

COPY . .

EXPOSE 8080

RUN ./gradlew build --refresh-dependencies --stacktrace

CMD ["./gradlew"]
