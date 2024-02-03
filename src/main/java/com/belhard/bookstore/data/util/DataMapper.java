package com.belhard.bookstore.data.util;

import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.User;

import java.util.List;

public interface DataMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<User> toEntity(List<UserDto> userDto);
    BookDto toDto(Book book);
    Book toEntity(BookDto bookDto);
    List<Book> toBookEntity(List<BookDto> bookDto);
}
