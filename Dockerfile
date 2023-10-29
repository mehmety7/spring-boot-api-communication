FROM openjdk:17
WORKDIR /app
ADD target/spring-kafka-application-0.0.1-SNAPSHOT.jar spring-kafka-application-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-kafka-application-0.0.1-SNAPSHOT.jar"]