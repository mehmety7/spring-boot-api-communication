package com.example.application.shopping.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class ProductEntity {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
}