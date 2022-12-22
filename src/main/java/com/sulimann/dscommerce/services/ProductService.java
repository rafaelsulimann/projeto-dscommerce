package com.sulimann.dscommerce.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sulimann.dscommerce.dto.CategoryDTO;
import com.sulimann.dscommerce.dto.ProductDTO;
import com.sulimann.dscommerce.dto.ProductMinDTO;
import com.sulimann.dscommerce.entities.Category;
import com.sulimann.dscommerce.entities.Product;
import com.sulimann.dscommerce.repositories.CategoryRepository;
import com.sulimann.dscommerce.repositories.ProductRepository;
import com.sulimann.dscommerce.services.exceptions.DatabaseException;
import com.sulimann.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto inexistente. id: " + productId));
        return new ProductDTO(product, this.categoryRepository.findAllCategoriesByProduct(productId));
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
        Page<Product> result = this.productRepository.searchByName(name, pageable);
        return result.map(x -> new ProductMinDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        try {
            Product entity = new Product();
            this.copyDtoToEntity(entity, productDTO);
            entity = this.productRepository.save(entity);
            List<CategoryDTO> categories = this.insertCategoriesIntoProduct(entity, productDTO);
            return new ProductDTO(categories, entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(
                    "Nome inválido! Já existe um produto com este nome. nome: " + productDTO.getName());
        }
    }

    @Transactional
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        try {
            Product entity = this.productRepository.getReferenceById(productId);
            this.copyDtoToEntity(entity, productDTO);
            entity = this.productRepository.save(entity);
            List<CategoryDTO> categories = this.updateCategoriesIntoProduct(entity, productDTO);
            return new ProductDTO(categories, entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto inexistente. Id: " + productId);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long productId) {
        try {
            this.productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Produto inexistente. id: " + productId);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(
                    "Deleção não realizada! Este produto está vinculado à pedidos existentes. id: " + productId);
        }
    }

    private void copyDtoToEntity(Product entity, ProductDTO productDTO) {
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());
    }

    @Transactional
    private List<CategoryDTO> insertCategoriesIntoProduct(Product product, ProductDTO productDTO) {
        List<CategoryDTO> categories = new ArrayList<>();
        productDTO.getCategories().stream().forEach(x -> {
            try {
                Category category = this.categoryRepository.findById(x.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Categoria inexistente. id: " + x.getId()));
                this.productRepository.saveProductCategory(product.getId(), category.getId());
                categories.add(new CategoryDTO(category));
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Erro ao inserir categoria! Categoria duplicada. id: " + x.getId());
            }
        });
        return categories;
    }

    @Transactional
    private List<CategoryDTO> updateCategoriesIntoProduct(Product product, ProductDTO productDTO) {
        this.productRepository.deleteProductCategories(product.getId());
        List<CategoryDTO> categories = new ArrayList<>();
        productDTO.getCategories().stream().forEach(x -> {
            try {
                Category category = this.categoryRepository.findById(x.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Categoria inexistente. id: " + x.getId()));
                this.productRepository.saveProductCategory(product.getId(), category.getId());
                categories.add(new CategoryDTO(category));
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Erro ao inserir categoria! Categoria duplicada. id: " + x.getId());
            }
        });
        return categories;
    }
}
