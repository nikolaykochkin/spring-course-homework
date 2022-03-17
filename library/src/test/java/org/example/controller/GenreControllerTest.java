package org.example.controller;

import org.example.model.Genre;
import org.example.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@TestPropertySource("classpath:disable_mongo.properties")
public class GenreControllerTest {
    public static final String GENRE_NAME = "Novel";
    public static final String GENRE_ID = "GENRE_ID";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    void should_ReturnGenre_When_GetGenreById() throws Exception {
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        given(genreRepository.findById(Mockito.anyString()))
                .willReturn(Optional.of(genre));

        mvc.perform(get("/api/genres/{id}", GENRE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GENRE_ID))
                .andExpect(jsonPath("$.name").value(GENRE_NAME));

        Mockito.verify(genreRepository, VerificationModeFactory.times(1))
                .findById(Mockito.anyString());

        Mockito.reset(genreRepository);
    }

    @Test
    void should_ReturnNotFound_When_GetGenreByInvalidId() throws Exception {
        given(genreRepository.findById(Mockito.anyString()))
                .willReturn(Optional.empty());

        mvc.perform(get("/api/genres/{id}", GENRE_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(genreRepository, VerificationModeFactory.times(1))
                .findById(Mockito.anyString());

        Mockito.reset(genreRepository);
    }

    @Test
    void should_ReturnGenres_When_GetAllGenres() throws Exception {
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);

        given(genreRepository.findAll())
                .willReturn(Collections.singletonList(genre));

        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(GENRE_ID))
                .andExpect(jsonPath("$[0].name").value(GENRE_NAME));

        Mockito.verify(genreRepository, VerificationModeFactory.times(1))
                .findAll();

        Mockito.reset(genreRepository);
    }

    @Test
    void should_ReturnGenre_When_SaveGenre() throws Exception {
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        String content = "{\"name\":\"" + GENRE_NAME + "\"}";

        given(genreRepository.save(Mockito.any()))
                .willReturn(genre);

        mvc.perform(post("/api/genres").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GENRE_ID))
                .andExpect(jsonPath("$.name").value(GENRE_NAME));

        Mockito.verify(genreRepository, VerificationModeFactory.times(1))
                .save(Mockito.any());

        Mockito.reset(genreRepository);
    }

    @Test
    void should_ReturnGenre_When_UpdateGenre() throws Exception {
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        String content = "{\"name\":\"" + GENRE_NAME + "\"}";

        given(genreRepository.save(Mockito.any()))
                .willReturn(genre);

        mvc.perform(put("/api/genres/{id}", GENRE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GENRE_ID))
                .andExpect(jsonPath("$.name").value(GENRE_NAME));

        Mockito.verify(genreRepository, VerificationModeFactory.times(1))
                .save(Mockito.any());

        Mockito.reset(genreRepository);
    }

    @Test
    void should_CallDelete_When_DeleteGenre() throws Exception {
        mvc.perform(delete("/api/genres/{id}", GENRE_ID))
                .andExpect(status().isOk());

        Mockito.verify(genreRepository, VerificationModeFactory.times(1))
                .deleteById(Mockito.anyString());

        Mockito.reset(genreRepository);
    }
}
