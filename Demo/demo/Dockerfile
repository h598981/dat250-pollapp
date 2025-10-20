# syntax=docker/dockerfile:1

######## Build stage ########
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Gradle wrapper + project files from the *current* module folder
COPY gradlew gradlew
COPY gradle  gradle
# If you don't have settings.gradle in this folder, delete the next line
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
COPY src src

# Make wrapper executable and build the fat jar
RUN chmod +x gradlew
RUN ./gradlew --no-daemon clean bootJar

######## Runtime stage ########
FROM eclipse-temurin:21-jre
WORKDIR /app

# Run as non-root
RUN adduser --disabled-password --gecos "" appuser && chown -R appuser /app
USER appuser

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
