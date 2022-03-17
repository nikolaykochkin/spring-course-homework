package org.example.controller;

import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.example.repository.BookRepository;
import org.example.util.JsonUtil;
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

@WebMvcTest(BookController.class)
@TestPropertySource("classpath:disable_mongo.properties")
public class BookControllerTest {
    public static final String BOOK_TITLE = "War and Peace";
    public static final String BOOK_ID = "BOOK_ID";

    public static final String AUTHOR_NAME = "Leo Tolstoy";
    public static final String AUTHOR_ID = "AUTHOR_ID";

    public static final String GENRE_NAME = "Novel";
    public static final String GENRE_ID = "GENRE_ID";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void should_ReturnBook_When_GetBookById() throws Exception {
        Book book = getBook();
        given(bookRepository.findById(Mockito.anyString()))
                .willReturn(Optional.of(book));

        mvc.perform(get("/api/books/{id}", BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(BOOK_ID))
                .andExpect(jsonPath("$.title").value(BOOK_TITLE))
                .andExpect(jsonPath("$.author.id").value(AUTHOR_ID))
                .andExpect(jsonPath("$.author.name").value(AUTHOR_NAME))
                .andExpect(jsonPath("$.genre.id").value(GENRE_ID))
                .andExpect(jsonPath("$.genre.name").value(GENRE_NAME));

        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .findById(Mockito.anyString());

        Mockito.reset(bookRepository);
    }

    @Test
    void should_ReturnNotFound_When_GetBookByInvalidId() throws Exception {
        given(bookRepository.findById(Mockito.anyString()))
                .willReturn(Optional.empty());

        mvc.perform(get("/api/books/{id}", BOOK_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .findById(Mockito.anyString());

        Mockito.reset(bookRepository);
    }

    @Test
    void should_ReturnBooks_When_GetAllBooks() throws Exception {
        Book book = getBook();

        given(bookRepository.findAll())
                .willReturn(Collections.singletonList(book));

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(BOOK_ID))
                .andExpect(jsonPath("$[0].title").value(BOOK_TITLE))
                .andExpect(jsonPath("$[0].author.id").value(AUTHOR_ID))
                .andExpect(jsonPath("$[0].author.name").value(AUTHOR_NAME))
                .andExpect(jsonPath("$[0].genre.id").value(GENRE_ID))
                .andExpect(jsonPath("$[0].genre.name").value(GENRE_NAME));

        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .findAll();

        Mockito.reset(bookRepository);
    }

    @Test
    void should_ReturnBook_When_SaveBook() throws Exception {
        Book book = getBook();
        byte[] content = JsonUtil.toJson(book);

        given(bookRepository.save(Mockito.any()))
                .willReturn(book);

        mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(BOOK_ID))
                .andExpect(jsonPath("$.title").value(BOOK_TITLE))
                .andExpect(jsonPath("$.author.id").value(AUTHOR_ID))
                .andExpect(jsonPath("$.author.name").value(AUTHOR_NAME))
                .andExpect(jsonPath("$.genre.id").value(GENRE_ID))
                .andExpect(jsonPath("$.genre.name").value(GENRE_NAME));

        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .save(Mockito.any());

        Mockito.reset(bookRepository);
    }

    @Test
    void should_ReturnBook_When_UpdateBook() throws Exception {
        Book book = getBook();
        byte[] content = JsonUtil.toJson(book);

        given(bookRepository.save(Mockito.any()))
                .willReturn(book);

        mvc.perform(put("/api/books/{id}", BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(BOOK_ID))
                .andExpect(jsonPath("$.title").value(BOOK_TITLE))
                .andExpect(jsonPath("$.author.id").value(AUTHOR_ID))
                .andExpect(jsonPath("$.author.name").value(AUTHOR_NAME))
                .andExpect(jsonPath("$.genre.id").value(GENRE_ID))
                .andExpect(jsonPath("$.genre.name").value(GENRE_NAME));

        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .save(Mockito.any());

        Mockito.reset(bookRepository);
    }

    @Test
    void should_CallDelete_When_DeleteBook() throws Exception {
        mvc.perform(delete("/api/books/{id}", BOOK_ID))
                .andExpect(status().isOk());

        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .deleteById(Mockito.anyString());

        Mockito.reset(bookRepository);
    }

    private Book getBook() {
        Book book = new Book();

        book.setId(BOOK_ID);
        book.setTitle(BOOK_TITLE);

        Author author = new Author(AUTHOR_ID, AUTHOR_NAME);
        book.setAuthor(author);

        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        book.setGenre(genre);

        return book;
    }
}
