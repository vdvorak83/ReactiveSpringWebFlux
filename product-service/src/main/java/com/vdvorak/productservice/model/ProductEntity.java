package com.vdvorak.productservice.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class ProductEntity {
    @Id
    private String id;
    private String description;
    private Integer price;
}
