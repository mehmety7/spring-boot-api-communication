# spring-kafka-application

## Requirements

For building and running the application: you need:

- **JDK 17** 
- **IDE** (i.e. IntelliJ Idea)
- **[Get Docker](https://docs.docker.com/get-docker/)**
- **Apache Maven 3.9.9**

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. You can use anyone.

- You can execute the main method in the com/finartz/restaurantapp/RestaurantappApplication.java class from your IDE.

- 'docker-compose up --build' (Recommended, Docker required)

- 'mvn spring-boot:run' (Maven required)

- 'mvnw clean install -U && java -jar restaurantapp-0.0.1-SNAPSHOT.jar' (Maven required)
