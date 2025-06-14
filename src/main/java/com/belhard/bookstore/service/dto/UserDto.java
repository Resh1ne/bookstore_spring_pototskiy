package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;

    @NotBlank(message = "{validation.user.email.null}")//will be localized by {code{
    @Email(message = "{validation.user.email.type}")
    private String email;

    @NotBlank //Custom localized message should be used instead of default
    @Length(min = 8, message = "{validation.user.password.min}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_]).{8,}$", message = "{validation.user.password.regexp}")
    private String password;
    private Integer age;
}
