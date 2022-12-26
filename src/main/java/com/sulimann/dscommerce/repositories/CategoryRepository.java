package com.sulimann.dscommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sulimann.dscommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    @Query(nativeQuery = true, value = "SELECT tb_categories.id, tb_categories.name "
    + "FROM tb_product_category "
    + "INNER JOIN tb_categories ON tb_product_category.category_id = tb_categories.id "
    + "WHERE tb_product_category.product_id = :productId")
    List<Category> findAllCategoriesByProduct(@Param("productId")Long productId);
    
}
