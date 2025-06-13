FROM eclipse-temurin:21-jdk-jammy

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=prd

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=stg"]
