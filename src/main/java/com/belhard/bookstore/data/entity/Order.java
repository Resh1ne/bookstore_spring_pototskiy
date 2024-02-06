package com.belhard.bookstore.data.entity;

import com.belhard.bookstore.data.entity.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {
    private Long id;
    private User user;
    private BigDecimal totalCost;
    private Status status;
    private List<OrderInfo> items;
}
