package com.sulimann.dscommerce.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sulimann.dscommerce.dto.OrderDTO;
import com.sulimann.dscommerce.entities.Order;
import com.sulimann.dscommerce.entities.OrderItem;
import com.sulimann.dscommerce.entities.OrderStatus;
import com.sulimann.dscommerce.entities.Product;
import com.sulimann.dscommerce.projections.OrderDTOProjection;
import com.sulimann.dscommerce.repositories.OrderItemRepository;
import com.sulimann.dscommerce.repositories.OrderRepository;
import com.sulimann.dscommerce.repositories.ProductRepository;
import com.sulimann.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long orderId){
        OrderDTOProjection order = this.orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido inexistente. id: " + orderId));
        return new OrderDTO(order, this.orderItemRepository.findAllOrderItemsIntoOrder(orderId));
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO){
    
        Order order = new Order();
    
        order.setClient(userService.authenticated());
        order.setMoment(LocalDateTime.now(ZoneId.of("UTC")));
        order.setStatus(OrderStatus.WAITING_PAYMENT);
    
        List<OrderItem> items = new ArrayList<>();
        
        orderDTO.getItems().stream().forEach(x -> {
            Product product = this.productRepository.findById(x.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto inexistente. id: " + x.getProductId()));
            OrderItem item = new OrderItem(order, product, x.getQuantity(), product.getPrice());
            items.add(item);
        });

        orderRepository.save(order);
        orderItemRepository.saveAll(items);
    
        return new OrderDTO(order, items);
    } 
}
