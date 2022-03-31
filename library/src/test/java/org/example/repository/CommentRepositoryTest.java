package org.example.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
class CommentRepositoryTest {
    public static final String EXAMPLE_COMMENT_TEXT = "Example Comment";
    public static final String FIRST_BOOK_TITLE = "War and Peace";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void should_FindAllComments_When_FindAll() {
//        List<Comment> expected = mongoTemplate.findAll(Comment.class);
//
//        List<Comment> actual = commentRepository.findAll();
//
//        assertIterableEquals(expected, actual);
    }

    @Test
    void should_FindNewComment_When_SaveComment() {
//        Comment expected = new Comment();
//
//        Query query = new Query();
//        query.addCriteria(Criteria.where("title").is(FIRST_BOOK_TITLE));
//        Book book = mongoTemplate.findOne(query, Book.class);
//
//        expected.setText(EXAMPLE_COMMENT_TEXT);
//        expected.setBook(book);
//
//        commentRepository.save(expected);
//        Comment actual = mongoTemplate.findById(expected.getId(), Comment.class);
//
//        assertEquals(expected, actual);
    }
}