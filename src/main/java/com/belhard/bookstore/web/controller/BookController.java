package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        BookDto book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("date", LocalDateTime.now().toString());
        return "book/book";
    }

    @GetMapping
    public String getBooks(Model model,
                           @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable page) {
        Page<BookDto> books = bookService.getAll(page);
        model.addAttribute("books", books.toList());
        model.addAttribute("page", books);
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
    public String editBookForm(@PathVariable Long id, Model model) {
        BookDto book = bookService.getById(id);
        model.addAttribute("book", book);
        return "book/editBookForm";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@ModelAttribute BookDto book) {
        BookDto updatedBook = bookService.update(book);
        return "redirect:/books/" + updatedBook.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
