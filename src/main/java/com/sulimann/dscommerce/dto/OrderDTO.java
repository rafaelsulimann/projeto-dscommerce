package com.sulimann.dscommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sulimann.dscommerce.entities.Order;
import com.sulimann.dscommerce.entities.OrderItem;
import com.sulimann.dscommerce.projections.OrderDTOProjection;
import com.sulimann.dscommerce.projections.OrderItemDTOProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime moment;
    private String status;
    private ClientDTO client;
    private PaymentDTO payment;
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(OrderDTOProjection projection, List<OrderItemDTOProjection> orderItemProjection){
        id = projection.getOrderId();
        moment = projection.getOrderMoment();
        status = projection.getStatus();
        client = new ClientDTO(projection.getUserId(), projection.getName());
        payment = projection.getPaymentId() == null && projection.getPaymentMoment() == null ? null : new PaymentDTO(projection.getPaymentId(), projection.getPaymentMoment());
        orderItemProjection.stream().forEach(x -> items.add(new OrderItemDTO(x)));
    }

    public OrderDTO(Order order, List<OrderItem> orderItems){
        id = order.getId();
        moment = order.getMoment();
        status = order.getStatus().name();
        client = new ClientDTO(order.getClient());
        for(OrderItem item : orderItems){
            items.add(new OrderItemDTO(item));
        }
    }

    public BigDecimal getTotal(){
        BigDecimal sum = new BigDecimal("0.0").setScale(2);
        for(OrderItemDTO orderItem : items){
            sum = sum.add(orderItem.getSubTotal());
        }
        return sum;
    }
}
