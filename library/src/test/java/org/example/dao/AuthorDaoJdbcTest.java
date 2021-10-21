package org.example.dao;

import org.example.exception.LibraryDataAccessException;
import org.example.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void should_ReturnCorrectAuthor_When_GetById() {
        Author expected = new Author(1, "Leo Tolstoy");
        Author actual = authorDaoJdbc.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnCorrectOptionalAuthor_When_GetByIdOptional() {
        Author expected = new Author(1, "Leo Tolstoy");
        Optional<Author> actual = authorDaoJdbc.getByIdOptional(1);
        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_ReturnAllAuthors_When_GetAll() {
        List<Author> expected = new ArrayList<>();
        expected.add(new Author(1, "Leo Tolstoy"));
        expected.add(new Author(2, "Fyodor Dostoevsky"));
        expected.add(new Author(3, "William Shakespeare"));
        expected.add(new Author(4, "Test Test"));

        List<Author> actual = authorDaoJdbc.getAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_ReturnNewAuthor_When_InsertNewAuthor() {
        Author expected = new Author("Author Author");

        long id = authorDaoJdbc.insert(expected);
        Author actual = authorDaoJdbc.getById(id);

        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_GetByIdDeletedAuthor() {
        long id = 4L;
        authorDaoJdbc.deleteById(id);
        assertThrows(LibraryDataAccessException.class, () -> authorDaoJdbc.getById(id));
    }

    @Test
    void should_UpdateAuthorName_When_Update() {
        long id = 1L;
        String expected = "Test";

        authorDaoJdbc.update(new Author(id, expected));
        String actual = authorDaoJdbc.getById(id).getName();

        assertEquals(expected, actual);
    }
}