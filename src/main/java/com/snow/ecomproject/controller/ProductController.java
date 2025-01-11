package com.snow.ecomproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.snow.ecomproject.model.Product;
import com.snow.ecomproject.repository.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProductsByFilters(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return productRepository.findByCategory_CategoryId(categoryId);
        }
        return productRepository.findAll();
    }
}

