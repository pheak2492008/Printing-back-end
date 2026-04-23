# Step 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/*.jar app.jar
# Expose the port (Render will override this with its own PORT variable)
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]