package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.data.repository.UserRepository;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto dto) {
        User user = toUserEntity(dto);
        User userCreated = userRepository.save(validateForCreate(user));
        log.info("Created new user with id: {}", userCreated.getId());
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
    @Transactional
    public List<UserDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(this::toUserDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto getById(long id) {
        return userRepository
                .findById(id)
                .map(this::toUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserDto update(UserDto dto) {
        User user = toUserEntity(dto);
        user.setId(dto.getId());
        User userCreated = userRepository.save(user);
        log.info("Updated user with id: {}", userCreated.getId());
        return toUserDto(userCreated);
    }

    @Override
    public void delete(long id) {
        if (!userRepository.delete(id)) {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
        log.info("Deleted user with id: {}", id);
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
