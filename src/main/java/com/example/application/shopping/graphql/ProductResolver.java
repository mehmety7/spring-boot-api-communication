package com.example.application.shopping.graphql;

import com.example.application.shopping.model.ProductEntity;
import com.example.application.shopping.service.MockProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductResolver {

    private final MockProductService mockProductService;

    public ProductResolver(MockProductService mockProductService) {
        this.mockProductService = mockProductService;
    }

    @QueryMapping
    public List<ProductEntity> products() {
        return mockProductService.getAllProducts();
    }

    @QueryMapping
    public ProductEntity product(@Argument Long id) {
        return mockProductService.getProduct(id);
    }

    @QueryMapping
    public List<ProductEntity> productsByCategory(@Argument String category) {
        return mockProductService.getProductsByCategory(category);
    }

    @MutationMapping
    public ProductEntity createProduct(@Argument ProductInput input) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(input.getName());
        productEntity.setDescription(input.getDescription());
        productEntity.setPrice(input.getPrice());
        productEntity.setQuantity(input.getQuantity());
        productEntity.setCategory(input.getCategory());
        return mockProductService.createProduct(productEntity);
    }

    @MutationMapping
    public ProductEntity updateProduct(@Argument Long id, @Argument ProductInput input) {
        ProductEntity productEntity = mockProductService.getProduct(id);
        if (productEntity == null) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }

        productEntity.setName(input.getName());
        productEntity.setDescription(input.getDescription());
        productEntity.setPrice(input.getPrice());
        productEntity.setQuantity(input.getQuantity());
        productEntity.setCategory(input.getCategory());

        return mockProductService.updateProduct(productEntity);
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        ProductEntity productEntity = mockProductService.getProduct(id);
        if (productEntity == null) {
            return false;
        }
        mockProductService.deleteProduct(id);
        return true;
    }
}