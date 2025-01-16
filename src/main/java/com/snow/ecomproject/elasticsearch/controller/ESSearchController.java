package com.snow.ecomproject.elasticsearch.controller;

import com.snow.ecomproject.elasticsearch.model.ProductDocument;
import com.snow.ecomproject.elasticsearch.service.ElasticsearchSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")
public class ESSearchController {

    @Autowired
    private ElasticsearchSearchService searchService;

    @GetMapping
    public List<ProductDocument> searchProducts(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Double maxMrp
    ) {
        if (searchTerm == null || searchTerm.isBlank()) {

            return List.of();
        }
        return searchService.searchProducts(searchTerm, maxMrp);
    }
}
