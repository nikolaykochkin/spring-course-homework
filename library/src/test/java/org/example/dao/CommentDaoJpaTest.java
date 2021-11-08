package org.example.dao;

import org.example.model.Book;
import org.example.model.Comment;
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
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {
    public static final long FIRST_COMMENT_ID = 1L;
    public static final long LAST_COMMENT_ID = 4L;
    public static final String EXAMPLE_COMMENT_TEXT = "Example Comment";
    public static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void should_FindExpectedComment_When_FindById() {
        Comment expected = em.find(Comment.class, FIRST_COMMENT_ID);

        Optional<Comment> actual = commentDaoJpa.findById(FIRST_COMMENT_ID);

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_FindAllComments_When_FindAll() {
        List<Comment> expected = em.getEntityManager()
                .createQuery("select c from Comment c", Comment.class)
                .getResultList();

        List<Comment> actual = commentDaoJpa.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewComment_When_SaveComment() {
        Comment expected = new Comment();
        Book book = em.find(Book.class, FIRST_BOOK_ID);

        expected.setText(EXAMPLE_COMMENT_TEXT);
        expected.setBook(book);

        commentDaoJpa.save(expected);
        em.flush();
        Comment actual = em.find(Comment.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void should_ChangeCommentText_When_UpdateComment() {
        Comment expected = new Comment();
        expected.setId(FIRST_COMMENT_ID);
        expected.setText(EXAMPLE_COMMENT_TEXT);

        commentDaoJpa.update(expected);
        em.flush();
        Comment actual = em.find(Comment.class, FIRST_COMMENT_ID);

        assertEquals(expected.getText(), actual.getText());
    }

    @Test
    void should_ReturnNoComment_When_DeleteById() {
        commentDaoJpa.deleteById(LAST_COMMENT_ID);
        em.flush();
        Comment comment = em.find(Comment.class, LAST_COMMENT_ID);
        assertNull(comment);
    }
}