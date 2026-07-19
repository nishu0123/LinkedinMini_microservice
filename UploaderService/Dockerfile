# ===========================
# Stage 1 : Build
# ===========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Working directory inside the container
WORKDIR /app

# Copy the entire multi-module project
COPY . .

# Build only UserService and its required modules
RUN mvn clean package -pl UploaderService -am -DskipTests

# ===========================
# Stage 2 : Runtime
# ===========================
FROM eclipse-temurin:21-jre

# Working directory
WORKDIR /app

# Copy only the generated JAR
COPY --from=builder /app/UploaderService/target/UploaderService-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 9057

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]




## ===========================
## Stage 1 : Build the project
## ===========================
#FROM maven:3.9.9-eclipse-temurin-21 AS builder
#
## Create working directory
#WORKDIR /app
#
## Copy pom.xml first
#COPY .mvn .mvn
#COPY mvnw .
#COPY pom.xml .
#
#RUN chmod +x mvnw
#
## Download dependencies
#RUN mvn dependency:go-offline
#
## Copy source code
#COPY src ./src
#
## Build the application
##RUN mvn clean package -DskipTests
#RUN ./mvnw clean package -DskipTests
#
#
## ===========================
## Stage 2 : Runtime
## ===========================
#FROM eclipse-temurin:21-jre
#
## Create working directory
#WORKDIR /app
#
## Copy only the generated jar from builder stage
##COPY --from=builder /app/target/<YOUR-JAR-NAME>.jar app.jar
#COPY --from=builder /app/target/UserService-0.0.1-SNAPSHOT.jar app.jar
#
## Application Port
#EXPOSE 9030
#
## Start application
#ENTRYPOINT ["java","-jar","app.jar"]