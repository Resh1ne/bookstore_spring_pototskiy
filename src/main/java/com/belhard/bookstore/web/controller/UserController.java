package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("date", LocalDateTime.now().toString());
        return "user/user";
    }

    @GetMapping
    public String getUsers(Model model,
                           @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable page) {
        Page<UserDto> users = userService.getAll(page);
        model.addAttribute("users", users.toList());
        model.addAttribute("page", users);
        return "user/users";
    }

    @GetMapping("/create")
    public String createUserForm() {
        return "user/createUserForm";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute @Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/createUserForm";
        }
        UserDto createdUser = userService.create(user);
        return "redirect:/users/" + createdUser.getId();
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "user/editUserForm";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute UserDto user) {
        UserDto updatedUser = userService.update(user);
        return "redirect:/users/" + updatedUser.getId();
    }
}
