package com.example.application.shopping.kafka.controller;

import com.example.application.shopping.kafka.component.KafkaProducer;
import com.example.application.shopping.kafka.component.model.ShoppingEvent;
import com.example.application.shopping.model.ProductEntity;
import com.example.application.shopping.service.MockProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaMessageController {

    private final KafkaProducer kafkaProducer;
    private final MockProductService mockProductService;

    @PostMapping("/shopping/product-viewed")
    public ResponseEntity<String> productViewed(
            @RequestParam Long userId,
            @RequestParam Long productId) {

        ProductEntity productEntity = mockProductService.getProduct(productId);
        if (productEntity == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        ShoppingEvent event = ShoppingEvent.productViewed(userId, productEntity);
        kafkaProducer.sendShoppingEvent(event);

        return ResponseEntity.ok("Product viewed event sent to Kafka");
    }
}