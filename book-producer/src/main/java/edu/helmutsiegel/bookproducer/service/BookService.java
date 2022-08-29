package edu.helmutsiegel.bookproducer.service;

import edu.helmutsiegel.bookproducer.exception.BookNotFoundException;
import edu.helmutsiegel.bookproducer.model.Book;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final List<Book> books = List.of(
            new Book.Builder().withId(1L)
                    .withTitle("Effective Java")
                    .withAuthor("Joshua Bloch")
                    .withCreated(LocalDate.of(2001, 1, 1))
                    .build(),
            new Book.Builder().withId(2L)
                    .withTitle("Clean Code")
                    .withAuthor("Robert Cecil Martin")
                    .withCreated(LocalDate.of(2008, 8, 1))
                    .build(),
            new Book.Builder().withId(3L)
                    .withTitle("Atomic Habits")
                    .withAuthor("James Clear")
                    .withCreated(LocalDate.of(2018, 10, 16))
                    .build()
    );

    public List<Book> getAll() {
        return this.books;
    }

    public Book getById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst().orElseThrow(BookNotFoundException::new);
    }

    public List<Book> getBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().matches("(.*)" + title + "(.*)"))
                .toList();
    }
}
