package org.example.repository;

import org.example.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DataMongoTest
class GenreRepositoryTest {
    public static final String FIRST_GENRE_NAME = "Novel";
    public static final String EXAMPLE_GENRE_NAME = "Example Genre";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void should_FindExpectedGenre_When_FindByName() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(FIRST_GENRE_NAME));
        Genre expected = mongoTemplate.findOne(query, Genre.class);

        Optional<Genre> actual = genreRepository.findGenreByName(FIRST_GENRE_NAME);

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllGenres_When_FindAll() {
        List<Genre> expected = mongoTemplate.findAll(Genre.class);

        List<Genre> actual = genreRepository.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewGenre_When_SaveGenre() {
        Genre expected = new Genre();
        expected.setName(EXAMPLE_GENRE_NAME);

        genreRepository.save(expected);
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(EXAMPLE_GENRE_NAME));
        Genre actual = mongoTemplate.findOne(query, Genre.class);

        assertEquals(expected, actual);
    }
}