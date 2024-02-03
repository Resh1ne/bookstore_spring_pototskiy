package com.belhard.bookstore.data.util.impl;

import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import com.belhard.bookstore.data.entity.enums.LanguagesOfTheBook;
import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.data.util.DataMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataMapperImpl implements DataMapper {
    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(Role.valueOf(user.getRole().name()));
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAge(user.getAge());
        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(Role.valueOf(userDto.getRole().name()));
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());
        return user;
    }

    @Override
    public List<User> toEntity(List<UserDto> userDto) {
        return userDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setPages(book.getPages());
        bookDto.setPublicationYear(book.getPublicationYear());
        bookDto.setGenre(GenresOfTheBook.valueOf(book.getGenre().name()));
        bookDto.setLanguage(LanguagesOfTheBook.valueOf(book.getLanguage().name()));
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setPages(bookDto.getPages());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setGenre(GenresOfTheBook.valueOf(bookDto.getGenre().name()));
        book.setLanguage(LanguagesOfTheBook.valueOf(bookDto.getLanguage().name()));
        book.setPrice(bookDto.getPrice());
        return book;
    }

    @Override
    public List<Book> toBookEntity(List<BookDto> bookDto) {
        return bookDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
