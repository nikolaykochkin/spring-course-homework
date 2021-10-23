package org.example.dao;

import org.example.exception.LibraryDataAccessException;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void should_ReturnCorrectBook_When_GetById() {
        Book expected = new Book(1, "War and Peace",
                new Author(1, "Leo Tolstoy"),
                new Genre(1, "Novel"));
        Book actual = bookDaoJdbc.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnCorrectOptionalBook_When_GetByIdOptional() {
        Book expected = new Book(1, "War and Peace",
                new Author(1, "Leo Tolstoy"),
                new Genre(1, "Novel"));
        Optional<Book> actual = bookDaoJdbc.getByIdOptional(1);
        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_ReturnAllBooks_When_GetAll() {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(1, "War and Peace",
                new Author(1, "Leo Tolstoy"),
                new Genre(1, "Novel")));
        expected.add(new Book(2, "Crime and Punishment",
                new Author(2, "Fyodor Dostoevsky"),
                new Genre(1, "Novel")));
        expected.add(new Book(3, "Venus and Adonis",
                new Author(3, "William Shakespeare"),
                new Genre(2, "Poem")));

        List<Book> actual = bookDaoJdbc.getAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_ReturnNewBook_When_InsertNewBook() {
        Book expected = new Book(4, "The Karamazov Brothers",
                new Author(2, "Fyodor Dostoevsky"),
                new Genre(1, "Novel"));

        long id = bookDaoJdbc.insert(expected);
        Book actual = bookDaoJdbc.getById(id);

        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_GetByIdDeletedBook() {
        long id = 3L;
        bookDaoJdbc.deleteById(id);
        assertThrows(LibraryDataAccessException.class, () -> bookDaoJdbc.getById(id));
    }

    @Test
    void should_UpdateBookName_When_Update() {
        long id = 1L;
        String expected = "Test";

        bookDaoJdbc.update(new Book(id, expected,
                new Author(1, "Leo Tolstoy"),
                new Genre(1, "Novel")));
        String actual = bookDaoJdbc.getById(id).getName();

        assertEquals(expected, actual);
    }
}