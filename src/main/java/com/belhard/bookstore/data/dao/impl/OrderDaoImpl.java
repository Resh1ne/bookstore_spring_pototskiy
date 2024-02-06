package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.OrderDao;
import com.belhard.bookstore.data.dto.OrderDto;
import com.belhard.bookstore.data.entity.enums.Status;
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
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {
    public static final String SELECT_BY_ID_QUERY = "SELECT o.id, o.user_id, o.total_cost, s.status_type  " +
            "FROM orders o JOIN statuses s ON o.status_type_id = s.id WHERE o.id = ?";
    public static final String SELECT_ALL_QUERY = "SELECT o.id, o.user_id, o.total_cost, s.status_type FROM orders o " +
            "JOIN statuses s ON o.status_type_id = s.id";
    private static final String INSERT_QUERY = "INSERT INTO orders (user_id, total_cost, status_type_id) " +
            "VALUES (:user_id, :total_cost, (SELECT id FROM statuses WHERE status_type = :status))";
    private static final String UPDATE_QUERY = "UPDATE orders SET user_id = :user_id, total_cost = :total_cost, status_type_id = " +
            "(SELECT id FROM statuses WHERE status_type = :status) WHERE id = :id";
    private static final String DELETE_QUERY = "DELETE FROM orders WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public OrderDto create(OrderDto order) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("user_id", order.getUserId())
                .addValue("total_cost", order.getTotalCost())
                .addValue("status", String.valueOf(order.getStatus()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_QUERY, params, keyHolder, new String[]{"id"});
        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .map(this::findById)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve the generated key"));
    }

    @Override
    public OrderDto findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, this::mapRow, id);
    }

    @Override
    public List<OrderDto> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, this::mapRow);
    }

    @Override
    public OrderDto update(OrderDto order) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("user_id", order.getUserId())
                .addValue("total_cost", order.getTotalCost())
                .addValue("status", String.valueOf(order.getStatus()))
                .addValue("id", order.getId());
        namedParameterJdbcTemplate.update(UPDATE_QUERY, params);
        return findById(order.getId());
    }

    @Override
    public boolean delete(Long id) {
        int rowsUpdated = jdbcTemplate.update(DELETE_QUERY, id);
        return rowsUpdated == 1;
    }

    private OrderDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        OrderDto order = new OrderDto();
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setTotalCost(resultSet.getBigDecimal("total_cost"));
        order.setStatus(Status.valueOf(resultSet.getString("status_type")));
        return order;
    }
}
