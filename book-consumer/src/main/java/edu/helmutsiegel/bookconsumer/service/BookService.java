package edu.helmutsiegel.bookconsumer.service;

import edu.helmutsiegel.bookconsumer.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
public class BookService {

    private static final String BOOK_BASE_URL = "/book";

    @Value("${book-producer.url}")
    private String producerUrl;

    public List<Book> getAll() {
        final Book[] books = new RestTemplate().getForObject(producerUrl + BOOK_BASE_URL, Book[].class);
        return Stream.of(books).toList();
    }

    public Book getById(Long id) {
        try {
            return new RestTemplate().getForObject(producerUrl + BOOK_BASE_URL + "/" + id, Book.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NoSuchElementException();
        }
    }
}
