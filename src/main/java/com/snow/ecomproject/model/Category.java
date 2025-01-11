package com.snow.ecomproject.model;

import lombok.Data;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Entity
@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("categoryId")
    private Long categoryId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("dateAdded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded = new Date(); // Automatically sets current timestamp
}
