package com.snow.ecomproject.repository;

import com.snow.ecomproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
// src/main/java/com/snow/ecomproject/repository/CategoryRepository.java

import com.snow.ecomproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name);
}
