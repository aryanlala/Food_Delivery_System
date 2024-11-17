# Step 1: Use Eclipse Temurin OpenJDK 21 image for the build stage
FROM eclipse-temurin:21-jdk-alpine AS build

# Install Maven and other required dependencies using apk (for Alpine-based image)
RUN apk update && \
    apk add --no-cache wget && \
    apk add --no-cache maven

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml /app/
COPY src /app/src/

# Build the JAR file using Maven
RUN mvn clean install

# Step 2: Use a smaller Eclipse Temurin JDK 21 image for runtime
FROM eclipse-temurin:21-jdk-alpine

# Set the maintainer information
MAINTAINER bits-pilani.com

# Copy the JAR file from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /food-delivery-1.0.0.jar

# Define the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "/food-delivery-1.0.0.jar"]
