# ===========================
# Stage 1 : Build
# ===========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Working directory inside the container
WORKDIR /app

# Copy the entire multi-module project
COPY . .

# Build only NotificationService and its required modules
RUN mvn clean package -pl NotificationService -am -DskipTests

# ===========================
# Stage 2 : Runtime
# ===========================
FROM eclipse-temurin:21-jre

# Working directory
WORKDIR /app

# Copy only the generated JAR
COPY --from=builder /app/NotificationService/target/NotificationService-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 9039

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
