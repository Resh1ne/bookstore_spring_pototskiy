package com.belhard.bookstore.data.repository.impl;

import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.UserRepository;
import com.belhard.bookstore.data.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;
    private final DataMapper dataMapper;
    @Override
    public User create(User user) {
        UserDto userDto = dataMapper.toDto(user);
        UserDto created = userDao.create(userDto);
        return dataMapper.toEntity(created);
    }

    @Override
    public User findById(Long id) {
        return dataMapper.toEntity(userDao.findById(id));
    }

    @Override
    public User findByEmail(String email) {
        return dataMapper.toEntity(userDao.findByEmail(email));
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return dataMapper.toUsersEntity(userDao.findByLastName(lastName));
    }

    @Override
    public List<User> findAll() {
        return dataMapper.toUsersEntity(userDao.findAll());
    }

    @Override
    public User update(User user) {
        UserDto userDto = dataMapper.toDto(user);
        UserDto updated = userDao.update(userDto);
        return dataMapper.toEntity(updated);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    public long countAll() {
        return userDao.countAll();
    }
}
