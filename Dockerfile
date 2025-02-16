# Stage 1: Build
FROM maven:3.9.2-eclipse-temurin-21-alpine as builder

# Set working directory
WORKDIR /app

# Copy the Maven project files
COPY ./pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY ./src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
