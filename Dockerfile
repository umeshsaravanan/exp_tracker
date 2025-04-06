# Use OpenJDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven project files
COPY . .

# Build the project
RUN apt update && apt install -y maven && mvn clean package -DskipTests

# Run the Spring Boot app
CMD ["java", "-jar", "target/ExpenseTracker-0.0.1-SNAPSHOT.jar"]
