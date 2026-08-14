# ==============================
# Stage 1: Build Spring Boot App
# ==============================
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and project files
COPY . .

# Give Maven wrapper execute permission
RUN chmod +x mvnw

# Build Spring Boot JAR
RUN ./mvnw clean package -DskipTests


# ==============================
# Stage 2: Run Spring Boot App
# ==============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy generated JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Spring Boot application port
EXPOSE 8181

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]