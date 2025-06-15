package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.data.repository.UserRepository;
import com.belhard.bookstore.service.EncryptionService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final ModelMapper mapper;

    @Override
    public UserDto create(UserDto dto) {
        String originalPassword = dto.getPassword();
        String hashedPassword = encryptionService.digest(originalPassword);
        dto.setPassword(hashedPassword);
        User user = mapper.map(dto, User.class);
        User userCreated = userRepository.save(validateForCreate(user));
        log.info("Created new user with id: {}", userCreated.getId());
        return mapper.map(userCreated, UserDto.class);
    }

    @Override
    public UserDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email " + email));
        String hashedPassword = encryptionService.digest(password);
        if (user.getPassword().equals(hashedPassword)) {
            return mapper.map(user, UserDto.class);
        }
        throw new ResourceNotFoundException("Invalid password!");
    }

    private User validateForCreate(User user) {
        user.setAge(null);
        user.setRole(Role.CUSTOMER);
        user.setLastName(null);
        user.setFirstName(null);
        return user;
    }

    @Override
    @Transactional
    public Page<UserDto> getAll(Pageable page) {
        return userRepository
                .findAll(page)
                .map(e -> mapper.map(e, UserDto.class));
    }

    @Override
    @Transactional
    public UserDto getById(long id) {
        return userRepository
                .findById(id)
                .map(e -> mapper.map(e, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserDto update(UserDto dto) {
        User user = mapper.map(dto, User.class);
        User userCreated = userRepository.save(user);
        log.info("Updated user with id: {}", userCreated.getId());
        return mapper.map(userCreated, UserDto.class);
    }

    @Override
    public void delete(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }
}
