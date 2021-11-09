package org.example.repository;

import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class BookRepositoryTest {
    public static final long FIRST_BOOK_ID = 1L;
    public static final long FIRST_AUTHOR_ID = 1L;
    public static final long FIRST_GENRE_ID = 1L;
    public static final long LAST_BOOK_ID = 1L;
    public static final String EXAMPLE_BOOK_NAME = "Example Book";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void should_FindExpectedBook_When_FindById() {
        Book expected = em.find(Book.class, FIRST_BOOK_ID);

        Optional<Book> actual = bookRepository.findById(FIRST_BOOK_ID);

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllBooks_When_FindAll() {
        List<Book> expected = em.getEntityManager()
                .createQuery("select b from Book b", Book.class)
                .getResultList();

        List<Book> actual = bookRepository.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewBook_When_SaveBook() {
        Book expected = new Book();
        Author author = new Author(FIRST_AUTHOR_ID, null);
        Genre genre = new Genre(FIRST_GENRE_ID, null);

        expected.setName(EXAMPLE_BOOK_NAME);
        expected.setAuthor(author);
        expected.setGenre(genre);

        bookRepository.save(expected);
        em.flush();
        Book actual = em.find(Book.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void should_ChangeBookName_When_UpdateBook() {
        Author author = new Author(FIRST_AUTHOR_ID, null);
        Genre genre = new Genre(FIRST_GENRE_ID, null);
        Book expected = new Book(FIRST_BOOK_ID, EXAMPLE_BOOK_NAME, author, genre, null);

        bookRepository.save(expected);
        em.flush();
        Book actual = em.find(Book.class, FIRST_BOOK_ID);

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void should_ReturnNoBook_When_DeleteById() {
        bookRepository.deleteById(LAST_BOOK_ID);
        em.flush();
        Book Book = em.find(Book.class, LAST_BOOK_ID);
        assertNull(Book);
    }
}