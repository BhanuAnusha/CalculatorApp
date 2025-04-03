# Stage 1: Build using Maven
FROM maven:3.9.5-eclipse-temurin-11 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Use JRE-only base image
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /app/target/calculator-app-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
