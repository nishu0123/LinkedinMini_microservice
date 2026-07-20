# ===========================
# Stage 1 : Build the project
# ===========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Create working directory
WORKDIR /app

# Copy pom.xml first
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
#RUN mvn clean package -DskipTests
RUN ./mvnw clean package -DskipTests


# ===========================
# Stage 2 : Runtime
# ===========================
FROM eclipse-temurin:21-jre

# Create working directory
WORKDIR /app

# Copy only the generated jar from builder stage
#COPY --from=builder /app/target/<YOUR-JAR-NAME>.jar app.jar
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Application Port
EXPOSE 6085

# Start application
ENTRYPOINT ["java","-jar","app.jar"]