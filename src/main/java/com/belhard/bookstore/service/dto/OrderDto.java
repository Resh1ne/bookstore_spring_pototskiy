package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private UserDto user;
    private BigDecimal totalCost;
    private Status status;
    private List<OrderItemDto> items;
}
