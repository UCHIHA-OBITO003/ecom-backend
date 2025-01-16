package com.snow.ecomproject.repository;

import com.snow.ecomproject.model.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    @Query(
            value = """
         SELECT * 
         FROM product 
         WHERE MATCH(p_name) AGAINST(:search IN BOOLEAN MODE)
      """,
            nativeQuery = true
    )
    List<Product> fullTextSearch(@Param("search") String search);


    @Query(
            value = """
         SELECT * 
         FROM product
         WHERE SOUNDEX(p_name) = SOUNDEX(:search)
      """,
            nativeQuery = true
    )
    List<Product> soundexSearch(@Param("search") String search);
}
