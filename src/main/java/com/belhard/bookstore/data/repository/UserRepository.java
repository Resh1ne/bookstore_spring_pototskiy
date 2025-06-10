package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByLastName(String lastName);
}
