package com.sulimann.dscommerce.dto;

import java.math.BigDecimal;

import com.sulimann.dscommerce.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductMinDTO {
    
    private Long id;
    private String name;
    private BigDecimal price;
    private String imgUrl;

    public ProductMinDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }
    
}
