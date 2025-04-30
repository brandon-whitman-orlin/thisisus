FROM openjdk:17-jdk-slim
ENV PORT=8080
WORKDIR /app
COPY target/image-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
