package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
    private static final String CREATION_QUERY = "INSERT INTO users " +
            "(first_name, last_name, role_id, email, \"password\", age) " +
            "VALUES (:first_name, :last_name, (SELECT id FROM roles r WHERE r.role = :role), :email, :password, :age)";
    private static final String FIND_ALL_QUERY = "SELECT u.id, u.first_name, u.last_name, r.role, u.email, u.password, u.age " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id ";
    private static final String FIND_BY_ID_QUERY = "SELECT u.id, u.first_name, u.last_name, r.role, u.email, u.password, u.age " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id " +
            "WHERE u.id = ?";
    private static final String FIND_BY_EMAIL = "SELECT u.id, u.first_name, u.last_name, r.role, u.email, u.password, u.age " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id " +
            "WHERE u.email = ?";
    private static final String FIND_BY_LAST_NAME = "SELECT u.id, u.first_name, u.last_name, r.role, u.email, u.password, u.age " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id " +
            "WHERE u.last_name = ?";
    private static final String UPDATE_QUERY = "UPDATE users " +
            "SET " +
            "first_name = :first_name, " +
            "last_name = :last_name, " +
            "email = :email," +
            "password = :password, " +
            "age = :age, " +
            "role_id = (SELECT id FROM roles r WHERE r.role = :role) " +
            "WHERE id = :id";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM users";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDto create(UserDto user) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("role", String.valueOf(user.getRole()))
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("age", user.getAge());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATION_QUERY, params, keyHolder, new String[]{"id"});
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }


    @Override
    public UserDto findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, this::mapRow, id);
    }

    private UserDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UserDto user = new UserDto();
        user.setAge(resultSet.getInt("age"));
        user.setEmail(resultSet.getString("email"));
        user.setId(resultSet.getLong("id"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.valueOf(resultSet.getString("role")));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        return user;
    }

    @Override
    public List<UserDto> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, this::mapRow);
    }

    @Override
    public UserDto update(UserDto user) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("age", user.getAge())
                .addValue("role", user.getRole().toString())
                .addValue("id", user.getId());
        int rowsUpdated = namedParameterJdbcTemplate.update(UPDATE_QUERY, params);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Can't update user with id: " + user.getId());
        }
        return findById(user.getId());
    }

    @Override
    public boolean delete(Long id) {
        int rowsUpdated = jdbcTemplate.update(DELETE_QUERY, id);
        return rowsUpdated == 1;
    }

    @Override
    public UserDto findByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_BY_EMAIL, this::mapRow, email);
    }

    @Override
    public List<UserDto> findByLastName(String lastName) {
        return jdbcTemplate.query(FIND_BY_LAST_NAME, this::mapRow, lastName);
    }

    @Override
    public long countAll() {
        Long count = jdbcTemplate.queryForObject(COUNT_QUERY, long.class);
        if (count == null) {
            return 0;
        }
        return count;
    }
}
