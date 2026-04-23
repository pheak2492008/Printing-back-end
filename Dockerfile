# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copy only pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render uses the $PORT environment variable
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]