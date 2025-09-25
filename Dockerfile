# ----- build -----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# cache de dependencias para acelerar builds
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# ----- runtime -----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

# Spring Boot ya toma server.port de env si lo definiste en application.yml (PORT)
ENTRYPOINT ["java","-jar","app.jar"]
