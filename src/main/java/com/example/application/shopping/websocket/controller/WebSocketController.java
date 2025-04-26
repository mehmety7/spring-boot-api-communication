package com.example.application.shopping.websocket.controller;

import com.example.application.shopping.model.ProductEntity;
import com.example.application.shopping.service.MockProductService;
import com.example.application.shopping.websocket.model.WebSocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MockProductService mockProductService;

    /**
     * Handles messages sent to /app/chat
     * Broadcasts the message to all subscribers of /topic/public
     */
    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public WebSocketMessage handleChatMessage(@Payload WebSocketMessage message) {
        log.info("Received chat message: {}", message);
        return message;
    }

    /**
     * Handles messages sent to /app/product
     * Broadcasts product updates to all subscribers of /topic/products
     */
    @MessageMapping("/product")
    @SendTo("/topic/products")
    public WebSocketMessage handleProductMessage(@Payload WebSocketMessage message) {
        log.info("Received product message: {}", message);

        if (message.getProductId() != null) {
            ProductEntity productEntity = mockProductService.getProduct(message.getProductId());
            if (productEntity != null) {
                return WebSocketMessage.productUpdate(productEntity);
            }
        }

        return message;
    }

    /**
     * Public method to send product updates from other parts of the application
     */
    public void sendProductUpdate(ProductEntity productEntity) {
        WebSocketMessage message = WebSocketMessage.productUpdate(productEntity);
        messagingTemplate.convertAndSend("/topic/products", message);
        log.info("Sent product update via WebSocket: {}", message);
    }

    /**
     * Public method to send notifications from other parts of the application
     */
    public void sendNotification(Long userId, String content) {
        WebSocketMessage message = WebSocketMessage.notification(userId, content);
        messagingTemplate.convertAndSend("/topic/user/" + userId + "/notifications", message);
        log.info("Sent notification via WebSocket: {}", message);
    }
}