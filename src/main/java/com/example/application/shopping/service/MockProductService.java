package com.example.application.shopping.service;

import com.example.application.shopping.model.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MockProductService {
    private final Map<Long, ProductEntity> products = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Initialize with some sample products
    public MockProductService() {
        createProduct(new ProductEntity(null, "Laptop", "High-performance laptop", 1299.99, 10, "Electronics"));
        createProduct(new ProductEntity(null, "Smartphone", "Latest smartphone model", 899.99, 20, "Electronics"));
        createProduct(new ProductEntity(null, "Headphones", "Noise-cancelling headphones", 199.99, 30, "Electronics"));
        createProduct(new ProductEntity(null, "T-shirt", "Cotton t-shirt", 19.99, 100, "Clothing"));
        createProduct(new ProductEntity(null, "Jeans", "Denim jeans", 49.99, 50, "Clothing"));
    }

    public ProductEntity createProduct(ProductEntity productEntity) {
        productEntity.setId(idGenerator.getAndIncrement());
        products.put(productEntity.getId(), productEntity);
        return productEntity;
    }

    public ProductEntity getProduct(Long id) {
        return products.get(id);
    }

    public List<ProductEntity> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public ProductEntity updateProduct(ProductEntity productEntity) {
        if (productEntity.getId() == null || !products.containsKey(productEntity.getId())) {
            throw new IllegalArgumentException("Product not found with id: " + productEntity.getId());
        }
        products.put(productEntity.getId(), productEntity);
        return productEntity;
    }

    public void deleteProduct(Long id) {
        products.remove(id);
    }

    public List<ProductEntity> getProductsByCategory(String category) {
        return products.values().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
    }
}