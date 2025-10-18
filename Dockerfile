# Stage 1: Build Stage
FROM maven:3.9.8-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /workspace

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn -q -e -B -DskipTests clean package

# Stage 2: Runtime Stage
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Create logs directory for file logging
#RUN mkdir -p /app/logs

# Create non-root user for security
#RUN addgroup -S spring && adduser -S spring -G spring
RUN groupadd -r spring && useradd -r -g spring spring


# Copy the jar from build stage
COPY --from=build /workspace/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring:spring

# Expose port
EXPOSE 8081

# install curl
#RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
USER root
RUN apt-get update -o Acquire::Retries=3 \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*
USER spring:spring

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", \
            "app.jar"]
