package com.example.application.shopping.rabbitmq.model;

import com.example.application.shopping.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShoppingMessage {

    private String id;
    private MessageType type;
    private LocalDateTime timestamp;
    private Long userId;
    private Long productId;
    private ProductEntity productEntity;
    private String message;

    public static ShoppingMessage productViewed(Long userId, ProductEntity productEntity) {
        ShoppingMessage message = new ShoppingMessage();
        message.setId(generateId());
        message.setType(MessageType.PRODUCT_VIEWED);
        message.setTimestamp(LocalDateTime.now());
        message.setUserId(userId);
        message.setProductId(productEntity.getId());
        message.setProductEntity(productEntity);
        message.setMessage("User " + userId + " viewed product " + productEntity.getName());
        return message;
    }

    public static ShoppingMessage productAddedToCart(Long userId, ProductEntity productEntity) {
        ShoppingMessage message = new ShoppingMessage();
        message.setId(generateId());
        message.setType(MessageType.PRODUCT_ADDED_TO_CART);
        message.setTimestamp(LocalDateTime.now());
        message.setUserId(userId);
        message.setProductId(productEntity.getId());
        message.setProductEntity(productEntity);
        message.setMessage("User " + userId + " added product " + productEntity.getName() + " to cart");
        return message;
    }

    public static ShoppingMessage productRemovedFromCart(Long userId, Long productId) {
        ShoppingMessage message = new ShoppingMessage();
        message.setId(generateId());
        message.setType(MessageType.PRODUCT_REMOVED_FROM_CART);
        message.setTimestamp(LocalDateTime.now());
        message.setUserId(userId);
        message.setProductId(productId);
        message.setMessage("User " + userId + " removed product " + productId + " from cart");
        return message;
    }

    public static ShoppingMessage cartCheckout(Long userId) {
        ShoppingMessage message = new ShoppingMessage();
        message.setId(generateId());
        message.setType(MessageType.CART_CHECKOUT);
        message.setTimestamp(LocalDateTime.now());
        message.setUserId(userId);
        message.setMessage("User " + userId + " checked out for products in cart");
        return message;
    }

    public static ShoppingMessage notification(Long userId, String notificationMessage) {
        ShoppingMessage message = new ShoppingMessage();
        message.setId(generateId());
        message.setType(MessageType.NOTIFICATION);
        message.setTimestamp(LocalDateTime.now());
        message.setUserId(userId);
        message.setMessage(notificationMessage);
        return message;
    }

    private static String generateId() {
        return randomUUID().toString();
    }

    public enum MessageType {
        PRODUCT_VIEWED,
        PRODUCT_ADDED_TO_CART,
        PRODUCT_REMOVED_FROM_CART,
        CART_CHECKOUT,
        ORDER_PLACED,
        PAYMENT_PROCESSED,
        NOTIFICATION
    }
}