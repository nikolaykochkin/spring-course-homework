package org.example.service;

import org.bson.types.ObjectId;
import org.example.exception.RelatedNotFoundException;
import org.example.model.Comment;
import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentService(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    public Mono<Comment> getCommentById(String id) {
        return Mono.just(id)
                .filter(ObjectId::isValid)
                .flatMap(commentRepository::findById);
    }

    public Flux<Comment> getByBookId(String bookId) {
        return Flux.just(bookId)
                .filter(ObjectId::isValid)
                .flatMap(commentRepository::findCommentsByBookId);
    }

    public Flux<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Mono<Comment> create(Mono<Comment> commentMono) {
        return commentMono
                .transform(checkBook())
                .flatMap(commentRepository::insert);
    }

    public Mono<Comment> update(Mono<Comment> commentMono, String id) {
        return commentMono
                .filter(comment -> ObjectId.isValid(id))
                .filterWhen(comment -> commentRepository.existsById(id))
                .transform(checkBook())
                .doOnNext(comment -> comment.setId(id))
                .flatMap(commentRepository::save);
    }

    public Mono<Void> delete(String id) {
        return commentRepository.deleteById(id);
    }

    private Function<Mono<Comment>, Mono<Comment>> checkBook() {
        return commentMono -> commentMono
                .flatMap(comment -> bookRepository.findById(comment.getBookId())
                        .switchIfEmpty(Mono.error(new RelatedNotFoundException("Book not found")))
                        .map(book -> comment));
    }
}
