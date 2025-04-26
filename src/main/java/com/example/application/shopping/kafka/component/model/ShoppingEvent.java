package com.example.application.shopping.kafka.component.model;

import com.example.application.shopping.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShoppingEvent {

    public static final String TOPIC_SHOPPING = "shopping-events";

    private String key;
    private EventType eventType;
    private LocalDateTime timestamp;
    private Long userId;
    private Long productId;
    private ProductEntity productEntity;
    private String message;

    public static ShoppingEvent productViewed(Long userId, ProductEntity productEntity) {
        ShoppingEvent event = new ShoppingEvent();
        event.setKey(userId + "-" + productEntity.getId());
        event.setEventType(EventType.PRODUCT_VIEWED);
        event.setTimestamp(LocalDateTime.now());
        event.setUserId(userId);
        event.setProductId(productEntity.getId());
        event.setProductEntity(productEntity);
        event.setMessage("User " + userId + " viewed product " + productEntity.getName());
        return event;
    }

    public enum EventType {
        PRODUCT_VIEWED
    }
}