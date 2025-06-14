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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    @Override
    public UserDto create(UserDto dto) {
        String originalPassword = dto.getPassword();
        String hashedPassword = encryptionService.digest(originalPassword);
        dto.setPassword(hashedPassword);
        User user = toUserEntity(dto);
        User userCreated = userRepository.save(validateForCreate(user));
        log.info("Created new user with id: {}", userCreated.getId());
        return toUserDto(userCreated);
    }

    @Override
    public UserDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.login.invalid", new Object[0], LocaleContextHolder.getLocale())
                ));
        String hashedPassword = encryptionService.digest(password);
        if (user.getPassword().equals(hashedPassword)) {
            return toUserDto(user);
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
    public Page<UserDto> getAll(Pageable page) {
        return userRepository
                .findAll(page)
                .map(this::toUserDto);
    }

    @Override
    @Transactional
    public UserDto getById(long id) {
        return userRepository
                .findById(id)
                .map(this::toUserDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notFound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
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
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
        userRepository.deleteById(id);
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
