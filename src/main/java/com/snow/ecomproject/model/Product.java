package com.snow.ecomproject.model;

import lombok.Data;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

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



    @JsonProperty("category")
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;
}
