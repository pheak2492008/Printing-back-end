# Stage 1: Build the application using Maven and Java 21
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build the jar file
RUN mvn clean package -DskipTests

# Stage 2: Run the application using Java 21 Runtime
FROM amazoncorretto:21-alpine
WORKDIR /app
# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]