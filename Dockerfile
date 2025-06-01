# Use an official Maven image to build the application
FROM maven:3.8.6-openjdk-8 AS builder
WORKDIR /app

# Copy the entire project and build it
COPY src/main/java/net/engineeringdigest/journalApp/controller .
RUN mvn clean package -DskipTests

# Use a lightweight OpenJDK image for running the application
FROM openjdk:8-jdk-alpine
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/journalApp-0.0.1-SNAPSHOT.jar .

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "journalApp-0.0.1-SNAPSHOT.jar"]
