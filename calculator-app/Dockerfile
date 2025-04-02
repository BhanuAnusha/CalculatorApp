# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# First download dependencies to cache them
RUN mvn dependency:go-offline
COPY src ./src
# Package with skipTests if you don't need tests in the image build
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the specific JAR file (not using wildcard)
# Make sure we're copying the spring-boot jar (usually has -SNAPSHOT or -boot in name)
COPY --from=build /app/target/*.jar app.jar

# Verify the JAR structure before running
RUN sh -c '\
    if ! unzip -p app.jar META-INF/MANIFEST.MF | grep -q "Main-Class:"; then \
        echo "ERROR: JAR file is not executable - check your Maven build"; \
        unzip -p app.jar META-INF/MANIFEST.MF; \
        exit 1; \
    fi'

# Use shell form to allow variable expansion
CMD ["java", "-jar", "app.jar"]

# Health check (optional)
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl -f http://localhost:8080/actuator/health || exit 1