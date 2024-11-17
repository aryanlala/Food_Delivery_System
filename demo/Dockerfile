# Use the official Maven image to build the app
FROM maven:3.9-openjdk-21-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml /app/
COPY src /app/src/

# Build the JAR file using Maven
RUN mvn clean install

# Use a smaller OpenJDK image to run the app (avoiding unnecessary build dependencies)
FROM openjdk:21-jdk

# Set the maintainer information
MAINTAINER bits-pilani.com

# Copy the JAR file from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /food-delivery-1.0.0.jar

# Define the entrypoint for the container
ENTRYPOINT ["java", "-jar", "/food-delivery-1.0.0.jar"]
