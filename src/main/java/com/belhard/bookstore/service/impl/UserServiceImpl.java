package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.data.repository.UserRepository;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto dto) {
        log.debug("The service method is called");
        User user = toUserEntity(dto);
        user = validateForCreate(user);
        User userCreated = userRepository.create(user);
        return toUserDto(userCreated);
    }

    private User validateForCreate(User user) {
        user.setAge(null);
        user.setRole(Role.CUSTOMER);
        user.setLastName(null);
        user.setFirstName(null);
        return user;
    }
    private User toUserEntity(UserDto dto) {
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
        return userRepository.findAll()
                .stream()
                .map(this::toUserDto)
                .toList();
    }

    @Override
    public UserDto getById(long id) {
        log.debug("The service method is called");
        User userEntity = userRepository.findById(id);
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
        User userCreated = userRepository.update(user);
        return toUserDto(userCreated);
    }

    @Override
    public void delete(long id) {
        log.debug("The service method is called");
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RuntimeException("Book with id: " + id + " not found");
        }
        userRepository.delete(id);
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
