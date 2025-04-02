# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage - using JDK instead of JRE for better diagnostics
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the specific JAR file (not wildcard)
COPY --from=build /app/target/calculator-app-*.jar app.jar

# Verify using jar command (available in JDK)
RUN jar xf app.jar META-INF/MANIFEST.MF && \
    if ! grep -q "Main-Class:" META-INF/MANIFEST.MF; then \
        echo "ERROR: Not an executable JAR - check your Maven build!"; \
        echo "=== MANIFEST CONTENTS ==="; \
        cat META-INF/MANIFEST.MF; \
        echo "=== JAR CONTENTS ==="; \
        jar tf app.jar | head -20; \
        exit 1; \
    fi

ENTRYPOINT ["java", "-jar", "app.jar"]