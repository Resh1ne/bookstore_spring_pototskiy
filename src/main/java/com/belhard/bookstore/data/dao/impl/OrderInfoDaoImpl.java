package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.OrderInfoDao;
import com.belhard.bookstore.data.dto.OrderInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class OrderInfoDaoImpl implements OrderInfoDao {
    public static final String SELECT_BY_ID_QUERY = "SELECT o.id, o.order_id, o.book_id, o.quantity, o.price FROM order_items o WHERE id = ?";
    public static final String SELECT_ALL_QUERY = "SELECT o.id, o.order_id, o.book_id, o.quantity, o.price FROM order_items o";
    public static final String INSERT_QUERY = "INSERT INTO order_items (order_id, book_id, quantity, price) VALUES (:order_id, :book_id, :book_quantity, :book_price)";
    public static final String DELETE_QUERY = "DELETE FROM order_items WHERE id = ?";
    public static final String UPDATE_QUERY = "UPDATE order_items SET order_id = :order_id, book_id = :book_id, quantity = :quantity, price = :price WHERE id = :id";
    public static final String SELECT_BY_ORDER_ID_QUERY = "SELECT o.id, o.order_id, o.book_id, o.quantity, o.price FROM order_items o WHERE order_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public OrderInfoDto create(OrderInfoDto orderInfo) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("order_id", orderInfo.getOrderId())
                .addValue("book_id", orderInfo.getBookId())
                .addValue("book_quantity", orderInfo.getBookQuantity())
                .addValue("book_price", orderInfo.getBookPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_QUERY, params, keyHolder, new String[]{"id"});
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public OrderInfoDto findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, this::mapRow, id);
    }

    @Override
    public List<OrderInfoDto> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_QUERY, this::mapRow);
    }

    @Override
    public OrderInfoDto update(OrderInfoDto orderInfo) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", orderInfo.getId())
                .addValue("order_id", orderInfo.getOrderId())
                .addValue("book_id", orderInfo.getBookId())
                .addValue("book_quantity", orderInfo.getBookQuantity())
                .addValue("book_price", orderInfo.getBookPrice());

        namedParameterJdbcTemplate.update(UPDATE_QUERY, params);
        return findById(orderInfo.getId());
    }

    @Override
    public boolean delete(Long id) {
        int rowsUpdated = jdbcTemplate.update(DELETE_QUERY, id);
        return rowsUpdated == 1;
    }

    @Override
    public List<OrderInfoDto> findByOrderId(Long id) {
        return jdbcTemplate.query(SELECT_BY_ORDER_ID_QUERY, this::mapRow, id);
    }

    private OrderInfoDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        orderInfoDto.setId(resultSet.getLong("id"));
        orderInfoDto.setOrderId(resultSet.getLong("order_id"));
        orderInfoDto.setBookId(resultSet.getLong("book_id"));
        orderInfoDto.setBookQuantity(resultSet.getInt("quantity"));
        orderInfoDto.setBookPrice(resultSet.getBigDecimal("price"));
        return orderInfoDto;
    }
}
