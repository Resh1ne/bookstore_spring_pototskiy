package com.belhard.bookstore.data.repository.impl;

import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dao.OrderDao;
import com.belhard.bookstore.data.dao.OrderInfoDao;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dto.OrderDto;
import com.belhard.bookstore.data.dto.OrderInfoDto;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderInfo;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.OrderRepository;
import com.belhard.bookstore.data.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderDao orderDao;
    private final OrderInfoDao orderInfoDao;
    private final UserDao userDao;
    private final BookDao bookDao;
    private final DataMapper dataMapper;

    @Override
    public Order create(Order order) {
        OrderDto orderDto = dataMapper.toDto(order);
        OrderDto createdDto = orderDao.create(orderDto);
        order.getItems().forEach(orderInfo -> {
            OrderInfoDto orderInfoDto = dataMapper.toDto(orderInfo);
            orderInfoDao.create(orderInfoDto);
        });

        return combineOrder(createdDto);
    }

    @Override
    public Order findById(Long id) {
        OrderDto orderDto = orderDao.findById(id);
        return combineOrder(orderDto);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll()
                .stream()
                .map(this::combineOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Order update(Order order) {
        OrderDto orderDto = dataMapper.toDto(order);
        OrderDto createdDto = orderDao.update(orderDto);
        orderInfoDao.findByOrderId(order.getId())
                .forEach(oldOrderInfo -> orderInfoDao.delete(oldOrderInfo.getId()));
        order.getItems().forEach(orderInfo -> {
            OrderInfoDto orderInfoDto = dataMapper.toDto(orderInfo);
            orderInfoDao.create(orderInfoDto);
        });

        return combineOrder(createdDto);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    private Order combineOrder(OrderDto orderDto) {
        Order order = dataMapper.toEntity(orderDto);
        User user = dataMapper.toEntity(userDao.findById(orderDto.getUserId()));
        order.setUser(user);
        List<OrderInfoDto> detailsDto = orderInfoDao.findByOrderId(orderDto.getId());
        List<OrderInfo> details = new ArrayList<>();
        detailsDto.forEach(dto -> {
            OrderInfo entity = dataMapper.toEntity(dto);
            Book book = dataMapper.toEntity(bookDao.findById(dto.getBookId()));
            entity.setBook(book);
            details.add(entity);
        });
        order.setItems(details);
        return order;
    }
}
