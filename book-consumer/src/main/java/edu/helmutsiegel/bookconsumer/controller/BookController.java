package edu.helmutsiegel.bookconsumer.controller;

import edu.helmutsiegel.bookconsumer.model.Book;
import edu.helmutsiegel.bookconsumer.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }
}

