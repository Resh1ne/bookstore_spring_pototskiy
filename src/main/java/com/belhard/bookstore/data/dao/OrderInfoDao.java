package com.belhard.bookstore.data.dao;

import com.belhard.bookstore.data.dto.OrderInfoDto;

import java.util.List;

public interface OrderInfoDao {
    OrderInfoDto create(OrderInfoDto orderInfo);

    OrderInfoDto findById(Long id);

    List<OrderInfoDto> findAll();

    OrderInfoDto update(OrderInfoDto orderInfo);

    boolean delete(Long id);

    List<OrderInfoDto> findByOrderId(Long id);
}
