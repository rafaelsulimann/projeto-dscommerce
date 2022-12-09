package com.sulimann.dscommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sulimann.dscommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("SELECT obj "
    + "FROM Product obj "
    + "WHERE UPPER(obj.name) LIKE CONCAT('%', UPPER(:name), '%')")
    Page<Product> searchByName(String name, Pageable pageable);
    
}
