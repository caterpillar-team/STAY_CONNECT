# Step 1: Build Stage
FROM gradle:8.7-jdk21 AS build-stage
WORKDIR /app

# Copy the Gradle project files
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Build the project
RUN gradle clean build -x test

# Step 2: Run Stage
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build-stage /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
