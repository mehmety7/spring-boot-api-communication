package com.example.application;

import com.example.application.component.KafkaConsumer;
import com.example.application.component.KafkaProducer;
import com.example.application.component.model.MyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class SpringKafkaApplication {

    private final KafkaProducer kafkaProducer;
    private final KafkaConsumer kafkaConsumer;

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Bean
    public void test() {
        MyEvent myEvent = new MyEvent("Hello!");
        kafkaProducer.sendFlightEvent(myEvent);
        kafkaConsumer.flightEventConsumer(myEvent);
    }

}
