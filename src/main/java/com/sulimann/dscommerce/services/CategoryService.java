package com.sulimann.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sulimann.dscommerce.dto.CategoryDTO;
import com.sulimann.dscommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<CategoryDTO> findAll(Pageable pageable){
        return categoryRepository.findAll(pageable).map(x -> new CategoryDTO(x));
    }
    
}
