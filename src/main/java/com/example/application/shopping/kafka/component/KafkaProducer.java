package com.example.application.shopping.kafka.component;

import com.example.application.shopping.kafka.component.model.ShoppingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendShoppingEvent(ShoppingEvent event) {
        String key = event.getKey();
        kafkaTemplate.send(ShoppingEvent.TOPIC_SHOPPING, key, event);
        log.info("Producer produced shopping event: {}", event);
    }
}
