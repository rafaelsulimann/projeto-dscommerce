package com.sulimann.dscommerce.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.sulimann.dscommerce.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    
    private Long id;

    @Size(min = 3, max = 80, message = "Nome deve ter entre 3 e 80 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;

    @Size(min = 10, message = "Descrição deve ter no mínimo 10 caracteres")
    @NotBlank(message = "Campo requerido")
    private String description;

    @Positive
    private BigDecimal price;
    private String imgUrl;

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }
    
}
