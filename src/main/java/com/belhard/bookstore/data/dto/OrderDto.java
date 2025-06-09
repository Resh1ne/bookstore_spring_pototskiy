package com.belhard.bookstore.data.dto;

import com.belhard.bookstore.data.entity.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private BigDecimal totalCost;
    private Status status;
}
