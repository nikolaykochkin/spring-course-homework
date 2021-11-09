package org.example.repository;

import org.example.model.Author;
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
class AuthorRepositoryTest {

    public static final long FIRST_AUTHOR_ID = 1L;
    public static final long LAST_AUTHOR_ID = 4L;
    public static final String EXAMPLE_AUTHOR_NAME = "Example Author";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void should_FindExpectedAuthor_When_FindById() {
        Author expected = em.find(Author.class, FIRST_AUTHOR_ID);

        Optional<Author> actual = authorRepository.findById(FIRST_AUTHOR_ID);

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllAuthors_When_FindAll() {
        List<Author> expected = em.getEntityManager()
                .createQuery("select a from Author a", Author.class)
                .getResultList();

        List<Author> actual = authorRepository.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewAuthor_When_SaveAuthor() {
        Author expected = new Author();
        expected.setName(EXAMPLE_AUTHOR_NAME);

        authorRepository.save(expected);
        em.flush();
        Author actual = em.find(Author.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void should_ChangeAuthorName_When_UpdateAuthor() {
        Author expected = new Author(FIRST_AUTHOR_ID, EXAMPLE_AUTHOR_NAME);

        authorRepository.save(expected);
        em.flush();
        Author actual = em.find(Author.class, FIRST_AUTHOR_ID);

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void should_ReturnNoAuthor_When_DeleteById() {
        authorRepository.deleteById(LAST_AUTHOR_ID);
        em.flush();
        Author author = em.find(Author.class, LAST_AUTHOR_ID);
        assertNull(author);
    }
}