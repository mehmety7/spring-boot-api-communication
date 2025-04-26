package com.example.application.shopping.rabbitmq.component;

import com.example.application.shopping.rabbitmq.config.RabbitMQConfig;
import com.example.application.shopping.rabbitmq.model.ShoppingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate customRabbitTemplate;

    public void sendShoppingMessage(ShoppingMessage message) {
        String routingKey = determineRoutingKey(message);
        customRabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_SHOPPING, routingKey, message);
        log.info("Sent shopping message to RabbitMQ: {}", message);
    }

    public void sendNotification(ShoppingMessage message) {
        customRabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_SHOPPING,
                RabbitMQConfig.ROUTING_KEY_NOTIFICATIONS,
                message);
        log.info("Sent notification message to RabbitMQ: {}", message);
    }

    private String determineRoutingKey(ShoppingMessage message) {
        return switch (message.getType()) {
            case PRODUCT_VIEWED -> "shopping.product.viewed";
            case PRODUCT_ADDED_TO_CART -> "shopping.cart.add";
            case PRODUCT_REMOVED_FROM_CART -> "shopping.cart.remove";
            case CART_CHECKOUT -> "shopping.cart.checkout";
            case ORDER_PLACED -> "shopping.order.placed";
            case PAYMENT_PROCESSED -> "shopping.payment.processed";
            case NOTIFICATION -> RabbitMQConfig.ROUTING_KEY_NOTIFICATIONS;
        };
    }
}