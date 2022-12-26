package com.sulimann.dscommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sulimann.dscommerce.entities.Order;
import com.sulimann.dscommerce.projections.OrderDTOProjection;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_orders.id AS orderId, tb_orders.moment AS orderMoment, tb_orders.status, tb_users.id AS userId, tb_users.name, tb_payments.order_id AS paymentId, tb_payments.moment AS paymentMoment "
    + "FROM TB_ORDERS "
    + "INNER JOIN tb_users ON tb_users.id = tb_orders.client_id "
    + "LEFT JOIN tb_payments ON tb_payments.order_id = tb_orders.id "
    + "WHERE tb_orders.id = :orderId")
    Optional<OrderDTOProjection> findOrderById(@Param("orderId") Long orderId);
}
