package edu.helmutsiegel.bookproducer.model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String title;
    private String author;
    private LocalDate created;

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getCreated() {
        return created;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String author;
        private LocalDate created;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder withCreated(LocalDate created) {
            this.created = created;
            return this;
        }

        public Book build(){
            Book book  = new Book();
            book.id = this.id;
            book.title = this.title;
            book.author = this.author;
            book.created = this.created;
            return book;
        }
    }
}
