# Dockerfile for Java Maven application
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Using the same Java version for runtime as build
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on (change if needed)
EXPOSE 8080

# Entry point to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]