package com.snow.ecomproject.specification;

import com.snow.ecomproject.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId);
        };
    }

    public static Specification<Product> hasStockGreaterThanOrEqual(Integer minStock) {
        return (root, query, criteriaBuilder) -> {
            if (minStock == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), minStock);
        };
    }

    public static Specification<Product> hasStockLessThanOrEqual(Integer maxStock) {
        return (root, query, criteriaBuilder) -> {
            if (maxStock == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("stock"), maxStock);
        };
    }

    public static Specification<Product> hasMrpBetween(Double minMrp, Double maxMrp) {
        return (root, query, criteriaBuilder) -> {
            if (minMrp == null && maxMrp == null) {
                return null;
            }
            if (minMrp != null && maxMrp != null) {
                return criteriaBuilder.between(root.get("mrp"), minMrp, maxMrp);
            }
            if (minMrp != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("mrp"), minMrp);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("mrp"), maxMrp);
        };
    }

    public static Specification<Product> hasDiscountedPriceBetween(Double minDiscountedPrice, Double maxDiscountedPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minDiscountedPrice == null && maxDiscountedPrice == null) {
                return null;
            }
            if (minDiscountedPrice != null && maxDiscountedPrice != null) {
                return criteriaBuilder.between(root.get("discountedPrice"), minDiscountedPrice, maxDiscountedPrice);
            }
            if (minDiscountedPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("discountedPrice"), minDiscountedPrice);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("discountedPrice"), maxDiscountedPrice);
        };
    }

    public static Specification<Product> hasNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.trim().isEmpty()) {
                return null;
            }
            String pattern = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("pName")), pattern);
        };
    }

}
