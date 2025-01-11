package com.snow.ecomproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.snow.ecomproject.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_CategoryId(Long categoryId);
}

