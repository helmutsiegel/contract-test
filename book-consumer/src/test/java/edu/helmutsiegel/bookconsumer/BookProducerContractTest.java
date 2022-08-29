package edu.helmutsiegel.bookconsumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import edu.helmutsiegel.bookconsumer.controller.BookController;
import edu.helmutsiegel.bookconsumer.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "book-producer", port = "18081")
@SpringBootTest
class BookProducerContractTest {

    @Autowired
    private BookController bookController;

    @Pact(provider = "book-producer", consumer = "book-consumer")
    public V4Pact getAllBooksFromProducer(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Get all books")
                .path("/api/book")
                .method("Get")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body("""
                        [{"id":1,"title":"Effective Java","author":"Joshua Bloch"},
                        {"id":2,"title":"Clean Code","author":"Robert Cecil Martin"},
                        {"id":3,"title":"Atomic Habits","author":"James Clear"}]
                        """)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getAllBooksFromProducer")
    void getAllBooksFromBookProducer(MockServer mockServer) {
        final List<Book> all = bookController.getAll();
        assertEquals(3, all.size());

        all.forEach(book -> {
            assertNotNull(book.getId());
            assertNotNull(book.getAuthor());
            assertNotNull(book.getTitle());
        });
    }

    @Pact(provider = "book-producer", consumer = "book-consumer")
    public V4Pact getBookByIdFromProducer(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Get book by id")
                .path("/api/book/1")
                .method("Get")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body("""
                        {"id":1,"title":"Effective Java","author":"Joshua Bloch","created":"2001-01-01"}
                        """)
                .uponReceiving("Get book by non-existing id ")
                .path("/api/book/404")
                .method("Get")
                .willRespondWith()
                .status(404)
                .headers(Map.of("Content-Type", "application/json"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getBookByIdFromProducer")
    void getBookByIdFromBookProducer(MockServer mockServer) {
        final Book book = bookController.getById(1L);
        assertNotNull(book);
        assertNotNull(book.getId());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getTitle());

        assertThrows(NoSuchElementException.class, () -> bookController.getById(404L));
    }
}