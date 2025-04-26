package com.example.application.shopping.rabbitmq.component;

import com.example.application.shopping.rabbitmq.config.RabbitMQConfig;
import com.example.application.shopping.rabbitmq.model.ShoppingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SHOPPING)
    public void receiveShoppingMessage(ShoppingMessage message) {
        log.info("Received shopping message from RabbitMQ: {}", message);

        // Process different types of shopping messages
        switch (message.getType()) {
            case PRODUCT_VIEWED:
                log.info("Product viewed: {}", message.getProductEntity().getName());
                break;
            case PRODUCT_ADDED_TO_CART:
                log.info("Product added to cart: {}", message.getProductEntity().getName());
                break;
            case PRODUCT_REMOVED_FROM_CART:
                log.info("Product removed from cart, product ID: {}", message.getProductId());
                break;
            case ORDER_PLACED:
                log.info("Order placed for user: {}", message.getUserId());
                break;
            case PAYMENT_PROCESSED:
                log.info("Payment processed for user: {}", message.getUserId());
                break;
            default:
                log.info("Unhandled message type: {}", message.getType());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICATIONS)
    public void receiveNotification(ShoppingMessage message) {
        log.info("Received notification from RabbitMQ: {}", message);

        // In a real application, this would send an email, SMS, or push notification
        log.info("Sending notification to user {}: {}", message.getUserId(), message.getMessage());
    }
}