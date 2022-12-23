package com.sulimann.dscommerce.projections;

import java.time.LocalDateTime;

public interface OrderDTOProjection {
    
    Long getOrderId();
    LocalDateTime getOrderMoment();
    String getStatus();
    Long getUserId();
    String getName();
    Long getPaymentId();
    LocalDateTime getPaymentMoment();

}
