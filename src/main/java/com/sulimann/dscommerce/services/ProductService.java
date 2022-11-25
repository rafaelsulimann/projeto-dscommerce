package com.sulimann.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sulimann.dscommerce.dto.ProductDTO;
import com.sulimann.dscommerce.entities.Product;
import com.sulimann.dscommerce.repositories.ProductRepository;
import com.sulimann.dscommerce.services.exceptions.DatabaseException;
import com.sulimann.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Produto inexistente. id: " + productId));
        return new ProductDTO(product);
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
        try {
            Product entity = productRepository.getReferenceById(productId);
            copyDtoToEntity(entity, productDTO);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto inexistente. Id: " + productId);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long productId){
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Produto inexistente. id: " + productId);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade referencial ao tentar deletar o produto. id: " + productId);
        }
    }

    private void copyDtoToEntity(Product entity, ProductDTO productDTO){
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());
    }
    
}
