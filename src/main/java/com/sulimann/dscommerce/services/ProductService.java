package com.sulimann.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sulimann.dscommerce.dto.ProductDTO;
import com.sulimann.dscommerce.entities.Product;
import com.sulimann.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long productId){
        return new ProductDTO(productRepository.findById(productId).get());
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = productRepository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO){
        Product entity = new Product();
        copyDtoToEntity(entity, productDTO);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long productId, ProductDTO productDTO){
        Product entity = productRepository.getReferenceById(productId);
        copyDtoToEntity(entity, productDTO);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    private void copyDtoToEntity(Product entity, ProductDTO productDTO){
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());
    }
    
}
