package org.example.dao;

import org.example.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {
    public static final long FIRST_GENRE_ID = 1L;
    public static final long LAST_GENRE_ID = 3L;
    public static final String EXAMPLE_GENRE_NAME = "Example Genre";

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void should_FindExpectedGenre_When_FindById() {
        Genre expected = em.find(Genre.class, FIRST_GENRE_ID);

        Optional<Genre> actual = genreDaoJpa.findById(FIRST_GENRE_ID);

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllGenres_When_FindAll() {
        List<Genre> expected = em.getEntityManager()
                .createQuery("select g from Genre g", Genre.class)
                .getResultList();

        List<Genre> actual = genreDaoJpa.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewGenre_When_SaveGenre() {
        Genre expected = new Genre();
        expected.setName(EXAMPLE_GENRE_NAME);

        genreDaoJpa.save(expected);
        em.flush();
        Genre actual = em.find(Genre.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void should_ChangeGenreName_When_UpdateGenre() {
        Genre expected = new Genre(FIRST_GENRE_ID, EXAMPLE_GENRE_NAME);

        genreDaoJpa.update(expected);
        em.flush();
        Genre actual = em.find(Genre.class, FIRST_GENRE_ID);

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void should_ReturnNoGenre_When_DeleteById() {
        genreDaoJpa.deleteById(LAST_GENRE_ID);
        em.flush();
        Genre genre = em.find(Genre.class, LAST_GENRE_ID);
        assertNull(genre);
    }
}