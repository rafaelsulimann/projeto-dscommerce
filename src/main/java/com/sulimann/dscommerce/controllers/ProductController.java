package com.sulimann.dscommerce.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sulimann.dscommerce.dto.ProductDTO;
import com.sulimann.dscommerce.dto.ProductMinDTO;
import com.sulimann.dscommerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = this.productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
        Page<ProductMinDTO> dto = this.productService.findAll(name, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> insert (@RequestBody @Valid ProductDTO productDTO){
        ProductDTO dto = this.productService.insert(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO){
        ProductDTO dto = this.productService.update(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}