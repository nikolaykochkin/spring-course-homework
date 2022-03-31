package org.example.repository;

import org.example.model.Genre;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    Mono<Genre> findGenreByName(String name);
}
