package org.example.repository;

import org.example.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findGenreByNameContains(String name);
    Optional<Genre> findGenreByName(String name);
}
