package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.connection.DataSource;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "INSERT INTO users " +
            "(first_name, last_name, role_id, email, \"password\", age) " +
            "VALUES (?, ?, (SELECT id FROM roles r WHERE r.role = ?), ?, ?, ?)";
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
            "first_name = ?, " +
            "last_name = ?, " +
            "email = ?," +
            "password = ?, " +
            "age = ?, " +
            "role_id = (SELECT id FROM roles r WHERE r.role = ?) " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM users";
    private static final Logger log = LogManager.getLogger(UserDaoImpl.class);


    @Override
    public User create(User user) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getRole().toString());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            setNullInt(6, user.getAge(), statement);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                return findById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create book: " + user);
    }

    private void setNullInt(int index, Integer value, PreparedStatement statement) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
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
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                User user = mapRow(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User update(User user) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            setNullInt(5, user.getAge(), statement);
            statement.setString(6, user.getRole().toString());
            statement.setLong(7, user.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return findById(user.getId());
            } else {
                throw new RuntimeException("Failed to update book. No rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(FIND_BY_LAST_NAME);
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = mapRow(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public long countAll() {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_QUERY);
            if (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("The values could not be calculated");
    }
}
