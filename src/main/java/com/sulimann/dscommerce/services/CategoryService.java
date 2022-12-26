package com.sulimann.dscommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sulimann.dscommerce.dto.CategoryDTO;
import com.sulimann.dscommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll(){
        return categoryRepository.findAll().stream().map(x -> new CategoryDTO(x)).toList();
    }
    
}
