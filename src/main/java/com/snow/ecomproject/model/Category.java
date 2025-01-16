

package com.snow.ecomproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

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
    private Date dateAdded = new Date();

    @JsonProperty("description")
    private String description;

    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

    @JsonProperty("profileUrl")
    private String profileUrl;

    @JsonProperty("photo")
    @Lob
    private byte[] photo;
}
