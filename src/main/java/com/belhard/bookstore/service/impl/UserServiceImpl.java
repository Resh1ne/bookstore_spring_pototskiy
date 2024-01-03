package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);


    @Override
    public UserDto create(UserDto dto) {
        log.debug("The service method is called");
        User user = toUserEntity(dto);
        user.setAge(null);
        user.setRole(Role.CUSTOMER);
        user.setLastName(null);
        user.setFirstName(null);
        user.setId(null);
        User bookCreated = userDao.create(user);
        return toUserDto(bookCreated);
    }

    private User toUserEntity(UserDto dto) {
        log.debug("The service method is called");
        User userEntity = new User();
        userEntity.setRole(dto.getRole());
        userEntity.setPassword(dto.getPassword());
        userEntity.setLastName(dto.getLastName());
        userEntity.setAge(dto.getAge());
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setEmail(dto.getEmail());
        return userEntity;
    }

    @Override
    public List<UserDto> getAll() {
        log.debug("The service method is called");
        return userDao.findAll()
                .stream()
                .map(this::toUserDto)
                .toList();
    }

    @Override
    public UserDto getById(long id) {
        log.debug("The service method is called");
        User userEntity = userDao.findById(id);
        if (userEntity == null) {
            throw new RuntimeException("No book with id: " + id);
        }
        return toUserDto(userEntity);
    }

    @Override
    public UserDto update(UserDto dto) {
        log.debug("The service method is called");
        User user = toUserEntity(dto);
        user.setId(dto.getId());
        User userCreated = userDao.update(user);
        return toUserDto(userCreated);
    }

    @Override
    public void delete(long id) {
        log.debug("The service method is called");
        User user = userDao.findById(id);
        if (user == null) {
            throw new RuntimeException("Book with id: " + id + " not found");
        }
        userDao.delete(id);
    }

    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
}
