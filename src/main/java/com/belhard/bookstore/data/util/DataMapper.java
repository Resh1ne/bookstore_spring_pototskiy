package com.belhard.bookstore.data.util;

import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.dto.OrderDto;
import com.belhard.bookstore.data.dto.OrderInfoDto;
import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderInfo;
import com.belhard.bookstore.data.entity.User;

import java.util.List;

public interface DataMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<User> toUsersEntity(List<UserDto> userDto);

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);

    List<Book> toBooksEntity(List<BookDto> bookDto);

    OrderDto toDto(Order entity);

    OrderInfoDto toDto(OrderInfo entity);

    Order toEntity(OrderDto dto);

    OrderInfo toEntity(OrderInfoDto dto);
}
