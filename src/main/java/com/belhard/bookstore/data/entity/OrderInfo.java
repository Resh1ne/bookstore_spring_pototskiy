package com.belhard.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfo {
    private Long id;
    private Book book;
    private Integer bookQuantity;
    private BigDecimal bookPrice;
}
