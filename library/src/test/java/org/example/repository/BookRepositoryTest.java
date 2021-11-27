package org.example.repository;

import org.example.model.Author;
import org.example.model.Book;
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
class BookRepositoryTest {
    public static final String FIRST_BOOK_TITLE = "War and Peace";
    public static final String EXAMPLE_AUTHOR_NAME = "Leo Tolstoy";
    public static final String EXAMPLE_GENRE_NAME = "Novel";
    public static final String EXAMPLE_BOOK_NAME = "Example Book";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void should_FindExpectedBook_When_FindByTitle() {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(FIRST_BOOK_TITLE));
        Book expected = mongoTemplate.findOne(query, Book.class);

        Optional<Book> actual = bookRepository.findBookByTitle(FIRST_BOOK_TITLE);

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllBooks_When_FindAll() {
        List<Book> expected = mongoTemplate.findAll(Book.class);

        List<Book> actual = bookRepository.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewBook_When_SaveBook() {
        Book expected = new Book();

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(EXAMPLE_AUTHOR_NAME));
        Author author = mongoTemplate.findOne(query, Author.class);

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("name").is(EXAMPLE_GENRE_NAME));
        Genre genre = mongoTemplate.findOne(query1, Genre.class);

        expected.setTitle(EXAMPLE_BOOK_NAME);
        expected.setAuthor(author);
        expected.setGenre(genre);

        bookRepository.save(expected);
        Book actual = mongoTemplate.findById(expected.getId(), Book.class);

        assertEquals(expected, actual);
    }
}