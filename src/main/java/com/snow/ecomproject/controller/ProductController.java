package com.snow.ecomproject.controller;

import com.snow.ecomproject.model.Product;
import com.snow.ecomproject.repository.ProductRepository;
import com.snow.ecomproject.service.SearchParserService;
import com.snow.ecomproject.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SearchParserService searchParserService;

    /**
     * Retrieves products based on various filters and search criteria.
     * Supports partial matching on product names.
     *
     * @param categoryId          Filter by category ID.
     * @param minStock            Minimum stock quantity.
     * @param maxStock            Maximum stock quantity.
     * @param minMrp              Minimum MRP.
     * @param maxMrp              Maximum MRP.
     * @param minDiscountedPrice  Minimum discounted price.
     * @param maxDiscountedPrice  Maximum discounted price.
     * @param search              Search term for product name.
     * @param page                Page number for pagination.
     * @param size                Page size for pagination.
     * @return Page of products matching the criteria.
     */
    @GetMapping
    public Page<Product> getProductsByFilters(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Integer maxStock,
            @RequestParam(required = false) Double minMrp,
            @RequestParam(required = false) Double maxMrp,
            @RequestParam(required = false) Double minDiscountedPrice,
            @RequestParam(required = false) Double maxDiscountedPrice,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasCategoryId(categoryId))
                .and(ProductSpecification.hasStockGreaterThanOrEqual(minStock))
                .and(ProductSpecification.hasStockLessThanOrEqual(maxStock))
                .and(ProductSpecification.hasMrpBetween(minMrp, maxMrp))
                .and(ProductSpecification.hasDiscountedPriceBetween(minDiscountedPrice, maxDiscountedPrice))
                .and(ProductSpecification.hasNameLike(search));

        return productRepository.findAll(spec, PageRequest.of(page, size));
    }

    /**
     * Adds a new product to the database.
     *
     * @param product Product details.
     * @return Saved product.
     */
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("q") String q) {
        // 1) Parse the user query for "under/above" numeric keywords, leftover text
        SearchParserService.ParsedSearch parsed = searchParserService.parseSearchQuery(q);

        String namePart = parsed.getNamePart(); // e.g. "laptop"
        Double minPrice = parsed.getMinPrice(); // e.g. null or 500
        Double maxPrice = parsed.getMaxPrice(); // e.g. 5000

        List<Product> results = new ArrayList<>();
        if (!namePart.isEmpty()) {
            // 2) Attempt FULLTEXT boolean mode query
            //    We'll prepend a plus '+' and append '*' for partial matching
            String booleanModeQuery = "+" + namePart.trim() + "*";
            results = productRepository.fullTextSearch(booleanModeQuery);

            // 3) If we got no results and namePart isn't empty, try SOUNDEX fallback
            if (results.isEmpty()) {
                results = productRepository.soundexSearch(namePart.trim());
            }
        } else {
            // If no text is provided, we can either return all or none.
            // Let's return all for demonstration:
            results = productRepository.findAll();
        }

        // 4) Filter by price range if specified
        List<Product> filtered = new ArrayList<>();
        for (Product p : results) {
            boolean matches = true;

            // "above X" => minPrice
            if (minPrice != null && p.getMrp() < minPrice) {
                matches = false;
            }
            // "under X" => maxPrice
            if (maxPrice != null && p.getMrp() > maxPrice) {
                matches = false;
            }

            // (OPTIONAL) If you also want to filter by discountedPrice,
            // add logic here ...
            // if (minPrice != null && p.getDiscountedPrice() < minPrice) { ... }
            // if (maxPrice != null && p.getDiscountedPrice() > maxPrice) { ... }

            if (matches) {
                filtered.add(p);
            }
        }

        return filtered;
    }

    // Additional endpoints as needed
}
