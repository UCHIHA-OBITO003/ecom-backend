// src/main/java/com/snow/ecomproject/model/Product.java

package com.snow.ecomproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("pName")
    private String pName;

    @JsonProperty("stock")
    private int stock;

    @JsonProperty("mrp")
    private double mrp;

    @JsonProperty("discountedPrice")
    private double discountedPrice;

    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    @JsonProperty("category")
    private Category category;
}
