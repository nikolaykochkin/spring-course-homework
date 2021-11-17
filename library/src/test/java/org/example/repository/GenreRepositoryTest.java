package org.example.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class GenreRepositoryTest {
    public static final long FIRST_GENRE_ID = 1L;
    public static final long LAST_GENRE_ID = 3L;
    public static final String EXAMPLE_GENRE_NAME = "Example Genre";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void should_FindExpectedGenre_When_FindById() {
//        Genre expected = em.find(Genre.class, FIRST_GENRE_ID);
//
//        Optional<Genre> actual = genreRepository.findById(FIRST_GENRE_ID);
//
//        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllGenres_When_FindAll() {
//        List<Genre> expected = em.getEntityManager()
//                .createQuery("select g from Genre g", Genre.class)
//                .getResultList();
//
//        List<Genre> actual = genreRepository.findAll();
//
//        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewGenre_When_SaveGenre() {
//        Genre expected = new Genre();
//        expected.setName(EXAMPLE_GENRE_NAME);
//
//        genreRepository.save(expected);
//        em.flush();
//        Genre actual = em.find(Genre.class, expected.getId());
//
//        assertEquals(expected, actual);
    }

    @Test
    void should_ChangeGenreName_When_UpdateGenre() {
//        Genre expected = new Genre(FIRST_GENRE_ID, EXAMPLE_GENRE_NAME);
//
//        genreRepository.save(expected);
//        em.flush();
//        Genre actual = em.find(Genre.class, FIRST_GENRE_ID);
//
//        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void should_ReturnNoGenre_When_DeleteById() {
//        genreRepository.deleteById(LAST_GENRE_ID);
//        em.flush();
//        Genre genre = em.find(Genre.class, LAST_GENRE_ID);
//        assertNull(genre);
    }
}