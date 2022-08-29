package edu.helmutsiegel.bookproducer.controller;

import edu.helmutsiegel.bookproducer.model.Book;
import edu.helmutsiegel.bookproducer.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping(value = "/ById/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping(value = "/searchByTitle/{title}")
    public List<Book> getByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }
}
