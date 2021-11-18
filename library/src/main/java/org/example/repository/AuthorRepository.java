package org.example.repository;

import org.example.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findAuthorByNameContains(String name);
    Optional<Author> findAuthorByName(String name);
}
