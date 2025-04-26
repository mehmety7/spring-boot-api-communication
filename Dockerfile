FROM openjdk:17
WORKDIR /app
ADD target/spring-boot-api-communication-0.0.1-SNAPSHOT.jar spring-boot-api-communication-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-api-communication-0.0.1-SNAPSHOT.jar"]
