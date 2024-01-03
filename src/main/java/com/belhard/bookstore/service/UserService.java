package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto dto);

    List<UserDto> getAll();

    UserDto getById(long id);

    UserDto update(UserDto dto);

    void delete(long id);
}
