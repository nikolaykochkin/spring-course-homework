package org.example.repository;

import org.example.model.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Mono<Author> findAuthorByName(String name);
}
