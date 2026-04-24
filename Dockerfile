# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run with Java 21
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Create folder for images (Temporary storage on Render)
RUN mkdir -p uploads/products

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]