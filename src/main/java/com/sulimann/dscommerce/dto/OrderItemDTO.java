package com.sulimann.dscommerce.dto;

import java.math.BigDecimal;

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
    private BigDecimal subTotal;

}
