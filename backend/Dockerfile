# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copy only pom.xml first to cache dependencies
COPY pom.xml .
Run mvn dependency:go-offline
#  Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests



# Runtime Stage
from openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build  /app/target/com.kinmelhub-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 9090

# Run the jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]

