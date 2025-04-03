# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-root user and group
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy JAR from builder
COPY --from=builder --chown=spring:spring /app/target/*.jar app.jar

# Verify JAR exists and is accessible
RUN sh -c '\
    if [ ! -f app.jar ]; then \
        echo "ERROR: JAR file not found!" && exit 1; \
    fi && \
    if ! java -jar app.jar --version; then \
        echo "ERROR: JAR file is not executable!" && exit 1; \
    fi'

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]