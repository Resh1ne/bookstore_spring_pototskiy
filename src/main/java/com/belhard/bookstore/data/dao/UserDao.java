package com.belhard.bookstore.data.dao;

import com.belhard.bookstore.data.dto.UserDto;

import java.util.List;

public interface UserDao {
    UserDto create(UserDto user);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto update(UserDto user);

    boolean delete(Long id);

    @SuppressWarnings("unused")
    UserDto findByEmail(String email);

    @SuppressWarnings("unused")
    List<UserDto> findByLastName(String lastName);

    long countAll();
}
