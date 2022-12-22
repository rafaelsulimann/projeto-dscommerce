package com.sulimann.dscommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sulimann.dscommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("SELECT obj "
    + "FROM Product obj "
    + "WHERE UPPER(obj.name) LIKE CONCAT('%', UPPER(:name), '%')")
    Page<Product> searchByName(String name, Pageable pageable);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO tb_product_category VALUES (:productId, :categoryId)")
    void saveProductCategory(@Param("productId")Long productId, @Param("categoryId")Long categoryId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_product_category WHERE product_id = :productId")
    void deleteProductCategories(@Param("productId")Long productId);

}
