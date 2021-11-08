package org.example.dao;

import org.example.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Optional<Comment> findById(long id);

    List<Comment> findAll();

    Comment save(Comment comment);

    void update(Comment comment);

    void deleteById(long id);
}
