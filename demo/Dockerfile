# Step 1: Use the official Maven image for building the app
FROM maven:3.9.1-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml /app/
COPY src /app/src/

# Build the JAR file using Maven
RUN mvn clean install

# Step 2: Use a smaller OpenJDK image to run the app
FROM eclipse-temurin:21-jdk-alpine

# Set the maintainer information
MAINTAINER bits-pilani.com

# Copy the JAR file from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /food-delivery-1.0.0.jar

# Define the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "/food-delivery-1.0.0.jar"]
