package com.example.application.shopping.websocket.model;

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
public class WebSocketMessage {

    private String id;
    private MessageType type;
    private LocalDateTime timestamp;
    private Long userId;
    private Long productId;
    private ProductEntity productEntity;
    private String content;

    public static WebSocketMessage productUpdate(ProductEntity productEntity) {
        WebSocketMessage message = new WebSocketMessage();
        message.setId(generateId());
        message.setType(MessageType.PRODUCT_UPDATE);
        message.setTimestamp(LocalDateTime.now());
        message.setProductId(productEntity.getId());
        message.setProductEntity(productEntity);
        message.setContent("Product updated: " + productEntity.getName());
        return message;
    }

    public static WebSocketMessage notification(Long userId, String notificationContent) {
        WebSocketMessage message = new WebSocketMessage();
        message.setId(generateId());
        message.setType(MessageType.NOTIFICATION);
        message.setTimestamp(LocalDateTime.now());
        message.setUserId(userId);
        message.setContent(notificationContent);
        return message;
    }

    private static String generateId() {
        return java.util.UUID.randomUUID().toString();
    }

    public enum MessageType {
        PRODUCT_UPDATE,
        CART_UPDATE,
        NOTIFICATION,
        CHAT_MESSAGE
    }
}