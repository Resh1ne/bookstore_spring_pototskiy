package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private User.Role role;
    private String email;
    private String password;
    private Integer age;
}
