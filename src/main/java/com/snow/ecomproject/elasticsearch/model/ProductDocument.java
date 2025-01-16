package com.snow.ecomproject.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product_index")
public class ProductDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private Long productId;

    @Field(type = FieldType.Text)
    private String pName;

    @Field(type = FieldType.Double)
    private double mrp;

    @Field(type = FieldType.Double)
    private double discountedPrice;

    @Field(type = FieldType.Integer)
    private int stock;

    @Field(type = FieldType.Long)
    private Long categoryId;

}
