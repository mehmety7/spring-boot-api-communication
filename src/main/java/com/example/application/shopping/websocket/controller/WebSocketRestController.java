package com.example.application.shopping.websocket.controller;

import com.example.application.shopping.model.ProductEntity;
import com.example.application.shopping.service.MockProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/websocket")
@RequiredArgsConstructor
public class WebSocketRestController {

    private final WebSocketController webSocketController;
    private final MockProductService mockProductService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<String> sendProductUpdate(@PathVariable Long productId) {
        ProductEntity productEntity = mockProductService.getProduct(productId);
        if (productEntity == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        webSocketController.sendProductUpdate(productEntity);
        return ResponseEntity.ok("Product update sent via WebSocket");
    }

    @PostMapping("/notification/{userId}")
    public ResponseEntity<String> sendNotification(
            @PathVariable Long userId,
            @RequestParam String message) {

        webSocketController.sendNotification(userId, message);
        return ResponseEntity.ok("Notification sent via WebSocket");
    }

    @PostMapping("/chat")
    public ResponseEntity<String> sendChatMessage(
            @RequestParam Long userId,
            @RequestParam String message) {

        // This will be handled by the WebSocket controller's handleChatMessage method
        // when a client sends a message to /app/chat
        return ResponseEntity.ok("To send chat messages, connect to WebSocket and send to /app/chat");
    }
}