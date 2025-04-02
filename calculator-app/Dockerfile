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

# Install unzip for verification (temporary)
RUN apt-get update && \
    apt-get install -y unzip && \
    rm -rf /var/lib/apt/lists/*

# Copy the specific JAR file
COPY --from=build /app/target/*.jar app.jar

# Verify the JAR structure before running
RUN sh -c '\
    if ! unzip -p app.jar META-INF/MANIFEST.MF | grep -q "Main-Class:"; then \
        echo "ERROR: JAR file is not executable - check your Maven build"; \
        echo "Found JAR files in target:"; \
        ls -l /app/target/; \
        unzip -p app.jar META-INF/MANIFEST.MF; \
        exit 1; \
    fi'

# Remove unzip to reduce image size (optional)
RUN apt-get purge -y unzip && apt-get autoremove -y

# Use shell form to allow variable expansion
CMD ["java", "-jar", "app.jar"]