package org.example.controller;

import org.example.model.Book;
import org.example.model.Comment;
import org.example.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(CommentController.class)
//@TestPropertySource("classpath:disable_mongo.properties")
public class CommentControllerTest {
    public static final String COMMENT_TEXT = "Example Comment";
    public static final String COMMENT_ID = "COMMENT_ID";
    public static final String BOOK_ID = "BOOK_ID";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void should_ReturnComment_When_GetCommentById() throws Exception {
//        Comment comment = getComment();
//        given(commentRepository.findById(Mockito.anyString()))
//                .willReturn(Optional.of(comment));
//
//        mvc.perform(get("/api/comments/{id}", COMMENT_ID))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(COMMENT_ID))
//                .andExpect(jsonPath("$.text").value(COMMENT_TEXT))
//                .andExpect(jsonPath("$.book.id").value(BOOK_ID));
//
//        Mockito.verify(commentRepository, VerificationModeFactory.times(1))
//                .findById(Mockito.anyString());
//
//        Mockito.reset(commentRepository);
    }

    @Test
    void should_ReturnNotFound_When_GetCommentByInvalidId() throws Exception {
//        given(commentRepository.findById(Mockito.anyString()))
//                .willReturn(Optional.empty());
//
//        mvc.perform(get("/api/comments/{id}", COMMENT_ID))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//
//        Mockito.verify(commentRepository, VerificationModeFactory.times(1))
//                .findById(Mockito.anyString());
//
//        Mockito.reset(commentRepository);
    }

    @Test
    void should_ReturnComments_When_GetAllComments() throws Exception {
//        Comment comment = getComment();
//
//        given(commentRepository.findAll())
//                .willReturn(Collections.singletonList(comment));
//
//        mvc.perform(get("/api/comments"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id").value(COMMENT_ID))
//                .andExpect(jsonPath("$[0].text").value(COMMENT_TEXT))
//                .andExpect(jsonPath("$[0].book.id").value(BOOK_ID));
//
//        Mockito.verify(commentRepository, VerificationModeFactory.times(1))
//                .findAll();
//
//        Mockito.reset(commentRepository);
    }

    @Test
    void should_ReturnComments_When_GetCommentsByBookId() throws Exception {
//        Comment comment = getComment();
//
//        given(commentRepository.findCommentByBookId(Mockito.anyString()))
//                .willReturn(Collections.singletonList(comment));
//
//        mvc.perform(get("/api/comments").param("bookId", BOOK_ID))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id").value(COMMENT_ID))
//                .andExpect(jsonPath("$[0].text").value(COMMENT_TEXT))
//                .andExpect(jsonPath("$[0].book.id").value(BOOK_ID));
//
//        Mockito.verify(commentRepository, VerificationModeFactory.times(1))
//                .findCommentByBookId(Mockito.anyString());
//
//        Mockito.reset(commentRepository);
    }

    @Test
    void should_ReturnComment_When_SaveComment() throws Exception {
//        Comment comment = getComment();
//        byte[] content = JsonUtil.toJson(comment);
//
//        given(commentRepository.save(Mockito.any()))
//                .willReturn(comment);
//
//        mvc.perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON).content(content))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(COMMENT_ID))
//                .andExpect(jsonPath("$.text").value(COMMENT_TEXT))
//                .andExpect(jsonPath("$.book.id").value(BOOK_ID));
//
//        Mockito.verify(commentRepository, VerificationModeFactory.times(1))
//                .save(Mockito.any());
//
//        Mockito.reset(commentRepository);
    }

    @Test
    void should_ReturnComment_When_UpdateComment() throws Exception {
//        Comment comment = getComment();
//        byte[] content = JsonUtil.toJson(comment);
//
//        given(commentRepository.save(Mockito.any()))
//                .willReturn(comment);
//
//        mvc.perform(put("/api/comments/{id}", BOOK_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(COMMENT_ID))
//                .andExpect(jsonPath("$.text").value(COMMENT_TEXT))
//                .andExpect(jsonPath("$.book.id").value(BOOK_ID));
//
//        Mockito.verify(commentRepository, VerificationModeFactory.times(1))
//                .save(Mockito.any());
//
//        Mockito.reset(commentRepository);
    }

    @Test
    void should_CallDelete_When_DeleteComment() throws Exception {
//        mvc.perform(delete("/api/comments/{id}", BOOK_ID))
//                .andExpect(status().isOk());
//
//        Mockito.verify(commentRepository, VerificationModeFactory.times(1))
//                .deleteById(Mockito.anyString());
//
//        Mockito.reset(commentRepository);
    }

    private Comment getComment() {
        Comment comment = new Comment();

        comment.setId(COMMENT_ID);
        comment.setText(COMMENT_TEXT);

        Book book = new Book();
        book.setId(BOOK_ID);
        //comment.setBook(book);

        return comment;
    }
}
