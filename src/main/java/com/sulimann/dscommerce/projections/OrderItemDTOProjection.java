package com.sulimann.dscommerce.projections;

import java.math.BigDecimal;

public interface OrderItemDTOProjection {
    
    Long getId();
    String getName();
    BigDecimal getPrice();
    Integer getQuantity();
    BigDecimal getSubTotal();
}
