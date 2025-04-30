FROM eclipse-temurin:21-jdk

# Set environment variable for the port
ENV PORT=8080

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/image-api-0.0.1-SNAPSHOT.jar app.jar

# Copy the resources folder into the container (default and three folders)
COPY src/main/resources /app/src/main/resources

# Expose the port that the application will run on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
