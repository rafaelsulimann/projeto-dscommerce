package com.sulimann.dscommerce.dto;

import com.sulimann.dscommerce.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Category entity){
        id = entity.getId();
        name = entity.getName();
    }
    
}
