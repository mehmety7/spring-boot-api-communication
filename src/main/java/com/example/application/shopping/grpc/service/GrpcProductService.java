package com.example.application.shopping.grpc.service;

import com.example.application.shopping.grpc.*;
import com.example.application.shopping.model.ProductEntity;
import com.example.application.shopping.service.MockProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GrpcProductService extends ProductServiceGrpc.ProductServiceImplBase {

    private final MockProductService mockProductService;

    @Override
    public void getAllProducts(Empty request, StreamObserver<ProductList> responseObserver) {
        List<ProductEntity> productEntities = mockProductService.getAllProducts();

        ProductList.Builder responseBuilder = ProductList.newBuilder();
        productEntities.forEach(p -> responseBuilder.addProducts(convertToGrpcProduct(p)));

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(ProductId request, StreamObserver<Product> responseObserver) {
        ProductEntity productEntity = mockProductService.getProduct(request.getId());

        if (productEntity != null) {
            responseObserver.onNext(convertToGrpcProduct(productEntity));
        } else {
            responseObserver.onNext(Product.newBuilder().build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByCategory(Category request, StreamObserver<ProductList> responseObserver) {
        List<ProductEntity> productEntities =
                mockProductService.getProductsByCategory(request.getCategory());

        ProductList.Builder responseBuilder = ProductList.newBuilder();
        productEntities.forEach(p -> responseBuilder.addProducts(convertToGrpcProduct(p)));

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createProduct(Product request, StreamObserver<Product> responseObserver) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setPrice(request.getPrice());
        productEntity.setQuantity(request.getQuantity());
        productEntity.setCategory(request.getCategory());

        ProductEntity createdProductEntity = mockProductService.createProduct(productEntity);

        responseObserver.onNext(convertToGrpcProduct(createdProductEntity));
        responseObserver.onCompleted();
    }

    @Override
    public void updateProduct(Product request, StreamObserver<Product> responseObserver) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(request.getId());
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setPrice(request.getPrice());
        productEntity.setQuantity(request.getQuantity());
        productEntity.setCategory(request.getCategory());

        try {
            ProductEntity updatedProductEntity = mockProductService.updateProduct(productEntity);
            responseObserver.onNext(convertToGrpcProduct(updatedProductEntity));
        } catch (IllegalArgumentException e) {
            responseObserver.onNext(Product.newBuilder().build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deleteProduct(ProductId request, StreamObserver<DeleteResponse> responseObserver) {
        ProductEntity productEntity = mockProductService.getProduct(request.getId());

        if (productEntity != null) {
            mockProductService.deleteProduct(request.getId());
            responseObserver.onNext(DeleteResponse.newBuilder().setSuccess(true).build());
        } else {
            responseObserver.onNext(DeleteResponse.newBuilder().setSuccess(false).build());
        }

        responseObserver.onCompleted();
    }

    private Product convertToGrpcProduct(ProductEntity product) {
        return Product.newBuilder()
                .setId(product.getId())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice())
                .setQuantity(product.getQuantity())
                .setCategory(product.getCategory())
                .build();
    }
}