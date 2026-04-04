# Stage 1: Build the backend and frontend together
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .

# Download dependencies initially
RUN mvn dependency:go-offline -B

# Copy source code and frontend
COPY src ./src
COPY frontend ./frontend

# Build the final unified JAR
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar from Stage 1
COPY --from=build /app/target/ExpenseTracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
# Run the Spring Boot app
CMD ["java", "-jar", "app.jar"]
