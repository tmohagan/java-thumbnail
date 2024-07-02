FROM openjdk:17-jdk-alpine
RUN ls -la /app
WORKDIR /app
COPY target/thumbnail-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "app.jar"]
