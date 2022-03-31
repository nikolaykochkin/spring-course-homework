package org.example.controller;

import org.example.model.Comment;
import org.example.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Comment>> getCommentById(@PathVariable("id") String id) {
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Comment> find(@RequestParam Optional<String> bookId) {
        return bookId.map(commentService::findByBookId)
                .orElseGet(commentService::findAll);
    }

    @PostMapping
    public Mono<Comment> save(@Valid @RequestBody Mono<Comment> comment) {
        return commentService.save(comment);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Comment>> update(@Valid @RequestBody Mono<Comment> comment, @PathVariable("id") String id) {
        return commentService.update(comment, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return commentService.delete(id);
    }
}
