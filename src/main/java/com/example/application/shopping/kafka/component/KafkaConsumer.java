package com.example.application.shopping.kafka.component;

import com.example.application.shopping.kafka.component.model.ShoppingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = ShoppingEvent.TOPIC_SHOPPING, groupId = "shopping-group")
    public void shoppingEventConsumer(ShoppingEvent event) {
        log.info("Consumer received shopping event: {}", event);

        // Process different types of shopping events
        if (Objects.requireNonNull(event.getEventType()) == ShoppingEvent.EventType.PRODUCT_VIEWED) {
            log.info("Product viewed: {}", event.getProductEntity().getName());
        } else {
            log.info("Unhandled event type: {}", event.getEventType());
        }
    }
}
