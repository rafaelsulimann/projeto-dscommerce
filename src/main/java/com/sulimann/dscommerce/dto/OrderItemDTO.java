package com.sulimann.dscommerce.dto;

import java.math.BigDecimal;

import com.sulimann.dscommerce.entities.OrderItem;
import com.sulimann.dscommerce.projections.OrderItemDTOProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDTO {
    
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String imgUrl;
    private BigDecimal subTotal;

    public OrderItemDTO(OrderItem entity){
        productId = entity.getProduct().getId();
        name = entity.getProduct().getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
        imgUrl = entity.getProduct().getImgUrl();
        subTotal = entity.getPrice().multiply(new BigDecimal(entity.getQuantity()));
    }

    public OrderItemDTO(OrderItemDTOProjection projection){
        productId = projection.getId();
        name = projection.getName();
        price = projection.getPrice();
        quantity = projection.getQuantity();
        imgUrl = projection.getImgUrl();
        subTotal = projection.getSubTotal();
    }

}
