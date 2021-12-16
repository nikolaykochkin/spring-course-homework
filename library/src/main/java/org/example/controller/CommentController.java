package org.example.controller;

import org.example.model.Comment;
import org.example.repository.CommentRepository;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable("id") String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Comment> find(@RequestParam Optional<String> bookId) {
        return bookId.map(commentRepository::findCommentByBookId)
                .orElseGet(commentRepository::findAll);
    }

    @PostMapping
    public Comment save(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @PutMapping("/{id}")
    public Comment update(@RequestBody Comment comment, @PathVariable("id") String id) {
        comment.setId(id);
        return commentRepository.save(comment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        commentRepository.deleteById(id);
    }
}
