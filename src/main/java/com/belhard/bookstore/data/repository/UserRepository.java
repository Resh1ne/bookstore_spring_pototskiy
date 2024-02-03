package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.User;

import java.util.List;

public interface UserRepository {
    User create(User user);

    User findById(Long id);

    User findByEmail(String email);

    List<User> findByLastName(String lastName);

    List<User> findAll();

    User update(User user);

    boolean delete(Long id);

    long countAll();
}
