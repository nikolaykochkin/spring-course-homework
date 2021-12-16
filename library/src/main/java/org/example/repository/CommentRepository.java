package org.example.repository;

import org.example.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query(value = "{ 'book.$id' : ObjectId(?0) }")
    List<Comment> findCommentByBookId(String bookId);
}
