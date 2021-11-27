package org.example.repository;

import org.example.model.Author;
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
class AuthorRepositoryTest {

    public static final String FIRST_AUTHOR_NAME = "Leo Tolstoy";
    public static final String EXAMPLE_AUTHOR_NAME = "Example Author";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void should_FindExpectedAuthor_When_FindByName() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(FIRST_AUTHOR_NAME));
        Author expected = mongoTemplate.findOne(query, Author.class);

        Optional<Author> actual = authorRepository.findAuthorByName(FIRST_AUTHOR_NAME);

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllAuthors_When_FindAll() {
        List<Author> expected = mongoTemplate.findAll(Author.class);

        List<Author> actual = authorRepository.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewAuthor_When_SaveAuthor() {
        Author expected = new Author();
        expected.setName(EXAMPLE_AUTHOR_NAME);

        authorRepository.save(expected);
        Author actual = mongoTemplate.findById(expected.getId(), Author.class);

        assertEquals(expected, actual);
    }
}