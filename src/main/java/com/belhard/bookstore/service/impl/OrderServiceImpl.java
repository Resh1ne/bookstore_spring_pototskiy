package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderItem;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.Status;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.data.repository.OrderItemRepository;
import com.belhard.bookstore.data.repository.OrderRepository;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderSimpleDto;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.exception.ResourceNotFoundException;
import com.belhard.bookstore.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper mapper;

    @Override
    public OrderDto create(OrderDto dto) {
        Order order = mapper.map(dto, Order.class);
        Order orderCreated = orderRepository.save(order);
        log.info("Created new order with id: {}", orderCreated.getId());
        return mapper.map(orderCreated, OrderDto.class);
    }

    @Override
    public List<OrderSimpleDto> getAll() {
        log.debug("Getting all orders with details.");
        return orderRepository
                .findAll()
                .stream()
                .map(e -> mapper.map(e, OrderSimpleDto.class))
                .toList();
    }

    @Override
    @Transactional
    public OrderDto getById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        Hibernate.initialize(order.getItems());
        Hibernate.initialize(order.getUser());
        return mapper.map(order, OrderDto.class);
    }

    @Override
    @Transactional
    public void addBookToOrder(Long bookId, UserDto userDto) {
        Order order = orderRepository.findByUserAndStatus(mapper.map(userDto, User.class), Status.PENDING)
                .orElseGet(() -> {
                    Order newOrder = new Order();
                    newOrder.setUser(mapper.map(userDto, User.class));
                    newOrder.setStatus(Status.PENDING);
                    return orderRepository.save(newOrder);
                });
        log.info("Working with order: {} (status: {})", order.getId(), order.getStatus());

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        log.info("Found book: {} - '{}'", book.getId(), book.getTitle());

        Optional<OrderItem> existingItem = orderItemRepository.findByOrderAndBook(order, book);
        if (existingItem.isPresent()) {
            OrderItem item = existingItem.get();
            log.info("Existing order item found (id: {}), increasing quantity", item.getId());
            item.setQuantity(item.getQuantity() + 1);
            orderItemRepository.save(item);
        } else {
            log.info("No existing order item found, creating new one");
            OrderItem newItem = new OrderItem();
            newItem.setBook(book);
            newItem.setOrder(order);
            newItem.setQuantity(1);
            newItem.setPrice(book.getPrice());
            orderItemRepository.save(newItem);

            if (order.getItems() == null) {
                order.setItems(new ArrayList<>());
            }
            order.getItems().add(newItem);
        }
        BigDecimal totalCost = calculateTotalCost(order);
        order.setTotalCost(totalCost);
        orderRepository.save(order);
        log.info("Order {} successfully updated. Total cost: {}", order.getId(), order.getTotalCost());
    }

    @Override
    @Transactional
    public OrderDto getCurrentPendingOrder(UserDto userDto) {
        Optional<Order> existingOrder = orderRepository.findByUserAndStatus(mapper.map(userDto, User.class), Status.PENDING);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            Hibernate.initialize(order.getItems());
            Hibernate.initialize(order.getUser());
            return mapper.map(order, OrderDto.class);
        } else {
            OrderDto newOrder = new OrderDto();
            newOrder.setUser(userDto);
            newOrder.setStatus(Status.PENDING);

            Order savedOrder = orderRepository.save(mapper.map(newOrder, Order.class));
            log.info("Created new pending order for user with id: {}", userDto.getId());
            return mapper.map(savedOrder, OrderDto.class);
        }
    }

    @Override
    @Transactional
    public void payOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (order.getStatus() != Status.PENDING) {
            throw new ValidationException("Only pending orders can be paid");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new ValidationException("Cannot pay empty order");
        }

        order.setStatus(Status.PAID);
        order.setTotalCost(calculateTotalCost(order));
        orderRepository.save(order);
        log.info("Order {} has been paid", id);
    }

    @Override
    public OrderDto update(OrderDto dto) {
        Order order = mapper.map(dto, Order.class);
        Order orderCreated = orderRepository.save(order);
        log.info("Updated order with id: {}", orderCreated.getId());
        return mapper.map(orderCreated, OrderDto.class);
    }

    @Override
    public void delete(long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order with id: " + id + " not found");
        }
        orderRepository.deleteById(id);
        log.info("Deleted order with id: {}", id);
    }

    public BigDecimal calculateTotalCost(Order order) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (OrderItem orderItem : order.getItems()) {
            BigDecimal itemCost = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }
}
