FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/*.jar Review-Service.jar
ENTRYPOINT ["java", "-jar", "Review-Service.jar"]