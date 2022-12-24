package com.sulimann.dscommerce.dto;

import com.sulimann.dscommerce.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDTO {
    
    private Long id;
    private String name;

    public ClientDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
    }

}