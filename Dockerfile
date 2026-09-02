# first stage
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src src
COPY pom.xml .
RUN ./mvnw clean package

# second stage
FROM eclipse-temurin:17-jre

WORKDIR /runningapp

COPY --from=builder /app/target/d13revision-0.0.1-SNAPSHOT.jar .

ENV SERVER_PORT=5000

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "d13revision-0.0.1-SNAPSHOT.jar"]
