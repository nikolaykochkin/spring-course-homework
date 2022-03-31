package org.example.service;

import org.example.model.Comment;
import org.example.repository.CommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Mono<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    public Flux<Comment> findByBookId(String bookId) {
        return commentRepository.findCommentsByBookId(bookId);
    }

    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Mono<Comment> save(Mono<Comment> comment) {
        return comment.flatMap(commentRepository::insert);
    }

    public Mono<Comment> update(Mono<Comment> comment, String id) {
        return comment
                .flatMap(c -> commentRepository.findById(id)
                        .map(comment1 -> c))
                .doOnNext(c -> c.setId(id))
                .flatMap(commentRepository::save);
    }

    public Mono<Void> delete(String id) {
        return commentRepository.deleteById(id);
    }
}
