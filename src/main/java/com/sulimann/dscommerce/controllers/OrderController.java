package com.sulimann.dscommerce.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sulimann.dscommerce.dto.OrderDTO;
import com.sulimann.dscommerce.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrderById(id));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ROLE_CLIENT')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }
}
