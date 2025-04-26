package com.example.application.shopping.rabbitmq.controller;

import com.example.application.shopping.model.ProductEntity;
import com.example.application.shopping.rabbitmq.component.RabbitMQProducer;
import com.example.application.shopping.rabbitmq.model.ShoppingMessage;
import com.example.application.shopping.service.MockProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/rabbitmq")
@RequiredArgsConstructor
public class RabbitMessageController {

    private final RabbitMQProducer rabbitMQProducer;
    private final MockProductService mockProductService;

    @PostMapping("/shopping/product-viewed")
    public ResponseEntity<String> productViewed(
            @RequestParam Long userId,
            @RequestParam Long productId) {

        ProductEntity productEntity = mockProductService.getProduct(productId);
        if (productEntity == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        ShoppingMessage message = ShoppingMessage.productViewed(userId, productEntity);
        rabbitMQProducer.sendShoppingMessage(message);

        return ResponseEntity.ok("Product viewed message sent to RabbitMQ");
    }

    @PostMapping("/shopping/add-to-cart")
    public ResponseEntity<String> addToCart(
            @RequestParam Long userId,
            @RequestParam Long cartId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity) {

        try {
            ProductEntity productEntity = mockProductService.getProduct(productId);
            if (productEntity == null) {
                return ResponseEntity.badRequest().body("Product not found");
            }

            log.info("Adding product to cart message is sending to RabbitMQ. userId: {}, cartId: {}, productId: {}, quantity: {}", userId, cartId, productId, quantity);

            ShoppingMessage message = ShoppingMessage.productAddedToCart(userId, productEntity);
            rabbitMQProducer.sendShoppingMessage(message);

            // Also send a notification
            ShoppingMessage notification = ShoppingMessage.notification(
                    userId,
                    "You added " + productEntity.getName() + " to your cart");
            rabbitMQProducer.sendNotification(notification);

            return ResponseEntity.ok("Product added to cart message sent to RabbitMQ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/shopping/remove-from-cart")
    public ResponseEntity<String> removeFromCart(
            @RequestParam Long userId,
            @RequestParam Long cartId,
            @RequestParam Long productId) {

        try {
            log.info("Removing cart from cart message is sending to RabbitMQ. userId: {}, cartId: {}, productId: {}", userId, cartId, productId);

            ShoppingMessage message = ShoppingMessage.productRemovedFromCart(userId, productId);
            rabbitMQProducer.sendShoppingMessage(message);

            return ResponseEntity.ok("Product removed from cart message sent to RabbitMQ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/shopping/checkout")
    public ResponseEntity<String> checkout(
            @RequestParam Long userId,
            @RequestParam Long cartId) {

        try {

            log.info("Checkout for userId: {}, cartId: {}", userId, cartId);

            ShoppingMessage message = ShoppingMessage.cartCheckout(userId);
            rabbitMQProducer.sendShoppingMessage(message);

            // Also send a notification
            ShoppingMessage notification = ShoppingMessage.notification(
                    userId,
                    "Your order has been placed!");
            rabbitMQProducer.sendNotification(notification);

            return ResponseEntity.ok("Cart checkout message sent to RabbitMQ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/notification")
    public ResponseEntity<String> sendNotification(
            @RequestParam Long userId,
            @RequestParam String message) {

        ShoppingMessage notification = ShoppingMessage.notification(userId, message);
        rabbitMQProducer.sendNotification(notification);

        return ResponseEntity.ok("Notification sent to RabbitMQ");
    }
}