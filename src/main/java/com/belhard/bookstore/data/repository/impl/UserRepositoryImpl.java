package com.belhard.bookstore.data.repository.impl;

import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    private static final String GET_ALL = "from User";
    private static final String FIND_BY_EMAIL = "SELECT u FROM User u WHERE u.email = :email";
    private static final String FIND_BY_LAST_NAME = "SELECT u FROM User u WHERE u.lastName = :lastName";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<User> findById(Long key) {
        return Optional.ofNullable(manager.find(User.class, key));
    }

    @Override
    public List<User> findAll() {
        return manager.createQuery(GET_ALL, User.class).getResultList();
    }

    @Override
    public User save(User entity) {
        if (entity.getId() == null) {
            manager.persist(entity);
            return entity;
        } else {
            return manager.merge(entity);
        }
    }

    @Override
    public boolean delete(Long key) {
        User user = manager.find(User.class, key);
        if (user == null) {
            return false;
        }
        manager.remove(user);
        return true;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return manager
                .createQuery(FIND_BY_EMAIL, User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return manager
                .createQuery(FIND_BY_LAST_NAME, User.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }
}
