package org.example.repository;

import org.example.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findBookByTitleContains(String title);
    Optional<Book> findBookByTitle(String title);
}
