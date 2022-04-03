package org.example.controller;

import org.example.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(AuthorController.class)
//@TestPropertySource("classpath:disable_mongo.properties")
public class AuthorControllerTest {
    public static final String AUTHOR_NAME = "Leo Tolstoy";
    public static final String AUTHOR_ID = "AUTHOR_ID";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void should_ReturnAuthor_When_GetAuthorById() throws Exception {
//        Author author = new Author(AUTHOR_ID, AUTHOR_NAME);
//        given(authorRepository.findById(Mockito.anyString()))
//                .willReturn(Optional.of(author));
//
//        mvc.perform(get("/api/authors/{id}", AUTHOR_ID))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(AUTHOR_ID))
//                .andExpect(jsonPath("$.name").value(AUTHOR_NAME));
//
//        Mockito.verify(authorRepository, VerificationModeFactory.times(1))
//                .findById(Mockito.anyString());
//
//        Mockito.reset(authorRepository);
    }

    @Test
    void should_ReturnNotFound_When_GetAuthorByInvalidId() throws Exception {
//        given(authorRepository.findById(Mockito.anyString()))
//                .willReturn(Optional.empty());
//
//        mvc.perform(get("/api/authors/{id}", AUTHOR_ID))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//
//        Mockito.verify(authorRepository, VerificationModeFactory.times(1))
//                .findById(Mockito.anyString());
//
//        Mockito.reset(authorRepository);
    }

    @Test
    void should_ReturnAuthors_When_GetAllAuthors() throws Exception {
//        Author author = new Author(AUTHOR_ID, AUTHOR_NAME);
//
//        given(authorRepository.findAll())
//                .willReturn(Collections.singletonList(author));
//
//        mvc.perform(get("/api/authors"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id").value(AUTHOR_ID))
//                .andExpect(jsonPath("$[0].name").value(AUTHOR_NAME));
//
//        Mockito.verify(authorRepository, VerificationModeFactory.times(1))
//                .findAll();
//
//        Mockito.reset(authorRepository);
    }

    @Test
    void should_ReturnAuthor_When_SaveAuthor() throws Exception {
//        Author author = new Author(AUTHOR_ID, AUTHOR_NAME);
//        String content = "{\"name\":\"" + AUTHOR_NAME + "\"}";
//
//        given(authorRepository.save(Mockito.any()))
//                .willReturn(author);
//
//        mvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON).content(content))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(AUTHOR_ID))
//                .andExpect(jsonPath("$.name").value(AUTHOR_NAME));
//
//        Mockito.verify(authorRepository, VerificationModeFactory.times(1))
//                .save(Mockito.any());
//
//        Mockito.reset(authorRepository);
    }

    @Test
    void should_ReturnAuthor_When_UpdateAuthor() throws Exception {
//        Author author = new Author(AUTHOR_ID, AUTHOR_NAME);
//        String content = "{\"name\":\"" + AUTHOR_NAME + "\"}";
//
//        given(authorRepository.save(Mockito.any()))
//                .willReturn(author);
//
//        mvc.perform(put("/api/authors/{id}", AUTHOR_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(AUTHOR_ID))
//                .andExpect(jsonPath("$.name").value(AUTHOR_NAME));
//
//        Mockito.verify(authorRepository, VerificationModeFactory.times(1))
//                .save(Mockito.any());
//
//        Mockito.reset(authorRepository);
    }

    @Test
    void should_CallDelete_When_DeleteAuthor() throws Exception {
//        mvc.perform(delete("/api/authors/{id}", AUTHOR_ID))
//                .andExpect(status().isOk());
//
//        Mockito.verify(authorRepository, VerificationModeFactory.times(1))
//                .deleteById(Mockito.anyString());
//
//        Mockito.reset(authorRepository);
    }
}
