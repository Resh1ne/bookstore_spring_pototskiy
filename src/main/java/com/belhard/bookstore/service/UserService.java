package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto create(UserDto dto);

    UserDto login(String email, String password);

    Page<UserDto> getAll(Pageable page);

    UserDto getById(long id);

    UserDto update(UserDto dto);

    void delete(long id);
}
