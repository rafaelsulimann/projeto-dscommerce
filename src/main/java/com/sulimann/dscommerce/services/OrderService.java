package com.sulimann.dscommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sulimann.dscommerce.dto.OrderDTO;
import com.sulimann.dscommerce.projections.OrderDTOProjection;
import com.sulimann.dscommerce.projections.OrderItemDTOProjection;
import com.sulimann.dscommerce.repositories.OrderItemRepository;
import com.sulimann.dscommerce.repositories.OrderRepository;
import com.sulimann.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long orderId){
        OrderDTOProjection order = this.orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido inexistente. id: " + orderId));
        List<OrderItemDTOProjection> orderItems = this.orderItemRepository.findAllOrderItemsIntoOrder(orderId);
        return new OrderDTO(order, orderItems);
    }
}
