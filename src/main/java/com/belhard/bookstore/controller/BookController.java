package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        BookDto book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("date", LocalDateTime.now().toString());
        return "book/book";
    }

    @GetMapping
    public String getBooks(Model model) {
        List<BookDto> books = bookService.getAll();
        model.addAttribute("books", books);
        return "book/books";
    }

    @GetMapping("/create")
    public String createBookForm() {
        return "book/createBookForm";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute BookDto book) {
        BookDto createdBook = bookService.create(book);
        return "redirect:/books/" + createdBook.getId();
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        BookDto book = bookService.getById(id);
        model.addAttribute("book", book);
        return "book/editBookForm";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@ModelAttribute BookDto book) {
        BookDto updatedBook = bookService.update(book);
        return "redirect:/books/" + updatedBook.getId();
    }
}
