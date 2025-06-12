package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSimpleDto {
    private Long id;
    private BigDecimal totalCost;
    private Status status;
}
