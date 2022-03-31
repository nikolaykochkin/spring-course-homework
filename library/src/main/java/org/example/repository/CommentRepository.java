package org.example.repository;

import org.example.model.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findCommentsByBookId(String bookId);
}
