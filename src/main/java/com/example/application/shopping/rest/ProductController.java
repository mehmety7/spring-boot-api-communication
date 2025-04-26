package com.example.application.shopping.rest;

import com.example.application.shopping.model.ProductEntity;
import com.example.application.shopping.service.MockProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final MockProductService mockProductService;

    public ProductController(MockProductService mockProductService) {
        this.mockProductService = mockProductService;
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        return ResponseEntity.ok(mockProductService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id) {
        ProductEntity productEntity = mockProductService.getProduct(id);
        if (productEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productEntity);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductEntity>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(mockProductService.getProductsByCategory(category));
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productEntity) {
        return new ResponseEntity<>(mockProductService.createProduct(productEntity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity productEntity) {
        if (productEntity.getId() == null) {
            productEntity.setId(id);
        } else if (!productEntity.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.ok(mockProductService.updateProduct(productEntity));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        ProductEntity productEntity = mockProductService.getProduct(id);
        if (productEntity == null) {
            return ResponseEntity.notFound().build();
        }
        mockProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}