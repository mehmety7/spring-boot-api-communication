package com.example.application.component;

import com.example.application.component.model.MyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    public static final String TOPIC_1 = "topic1";

    private final KafkaTemplate<String, MyEvent> kafkaTemplate;

    public void sendFlightEvent(MyEvent event){
        String key = event.getKey();
        kafkaTemplate.send(TOPIC_1, key , event);
        log.info("Producer produced the message {}", event);
        // write your handlers and post-processing logic, based on your use case
    }
}