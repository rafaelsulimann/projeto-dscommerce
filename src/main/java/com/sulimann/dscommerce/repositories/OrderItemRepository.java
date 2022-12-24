package com.sulimann.dscommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sulimann.dscommerce.entities.OrderItem;
import com.sulimann.dscommerce.projections.OrderItemDTOProjection;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_products.id, tb_products.name, tb_order_items.price, tb_order_items.quantity, tb_products.img_url AS imgUrl, SUM(tb_order_items.price * tb_order_items.quantity) AS subTotal "
    + "FROM tb_order_items "
    + "INNER JOIN tb_products ON tb_products.id = tb_order_items.product_id "
    + "WHERE tb_order_items.order_id = :orderId "
    + "GROUP BY tb_order_items.order_id, tb_products.id")
    List<OrderItemDTOProjection> findAllOrderItemsIntoOrder(@Param("orderId") Long orderId);

    
}
