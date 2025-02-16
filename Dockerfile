# Use OpenJDK 21 with Maven

FROM maven:3.9.6-eclipse-temurin-21 AS build

# Copy the project files
COPY . .
# Build the Maven project
RUN mvn clean package -DskipTests

# Use Slim version of OpenJDK 21 for the final runtime image
FROM eclipse-temurin:21-jdk-slim

# Copy only the built JAR file from the build stage
COPY --from=build /target/Smart-contact-manager-0.0.1-SNAPSHOT.jar Smart-contact-manager.jar

# Expose application port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Smart-contact-manager.jar"]


