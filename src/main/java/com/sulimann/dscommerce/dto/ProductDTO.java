package com.sulimann.dscommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.sulimann.dscommerce.entities.Category;
import com.sulimann.dscommerce.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO implements Serializable{
    
    private Long id;

    @Size(min = 3, max = 80, message = "Nome deve ter entre 3 e 80 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;

    @Size(min = 10, message = "Descrição deve ter no mínimo 10 caracteres")
    @NotBlank(message = "Campo requerido")
    private String description;

    @NotNull(message = "Campo requerido")
    @Positive
    private BigDecimal price;
    private String imgUrl;

    @NotEmpty(message = "Pelo menos uma categoria deve ser informada")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Product entity, List<Category> categoriesList) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        categoriesList.stream().forEach(x -> categories.add(new CategoryDTO(x)));
    }

    public ProductDTO(List<CategoryDTO> categoriesList, Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        categories = categoriesList;
    }  

}
