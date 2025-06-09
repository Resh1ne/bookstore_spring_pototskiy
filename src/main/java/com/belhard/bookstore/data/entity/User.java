package com.belhard.bookstore.data.entity;

import com.belhard.bookstore.data.entity.enums.Role;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private String password;
    private Integer age;
}
