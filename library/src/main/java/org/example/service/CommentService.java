package org.example.service;

import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.example.model.Book;
import org.example.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final Scanner scanner;
    private final PrintStream printStream;

    public CommentService(CommentRepository commentRepository,
                          BookRepository bookRepository,
                          @Value("#{T(java.lang.System).in}") InputStream inputStream,
                          @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String find(String id) {
        Optional<Comment> optionalComment = Optional.empty();
        try {
            optionalComment = commentRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error("Failed to find comment by id `{}`, cause `{}`", id, e.getMessage());
        }
        return optionalComment
                .map(Comment::toString)
                .orElse("Comment not found!");
    }

    public String list() {
        Optional<String> comments = Optional.empty();
        try {
            comments = Optional.of(commentRepository.findAll().stream()
                    .map(Comment::toString)
                    .collect(Collectors.joining("\n")));
        } catch (Exception e) {
            LOGGER.error("Failed to get comments list, cause `{}`", e.getMessage());
        }
        if (comments.isPresent() && !comments.get().isBlank()) {
            return comments.get();
        } else {
            return "Comments not found!";
        }
    }

    @Transactional
    public String insert() {
        printStream.print("Enter book title: ");
        Optional<Book> book = bookRepository.findBookByTitleContains(scanner.nextLine());
        if (book.isEmpty()) {
            return "Book not found!";
        }
        printStream.print("Enter text of comment: ");
        Comment comment = new Comment();
        comment.setText(scanner.nextLine());
        comment.setBook(book.get());
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            LOGGER.error("Failed to save comment, cause `{}`", e.getMessage());
            return "Something went wrong, the comment was not saved!";
        }
        return comment.toString();
    }

    @Transactional
    public String update(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            return "Comment not found!";
        }
        printStream.println("Found comment: ");
        printStream.println(comment);
        printStream.print("Enter new comment name: ");
        comment.get().setText(scanner.nextLine());
        try {
            commentRepository.save(comment.get());
        } catch (Exception e) {
            LOGGER.error("Failed to update comment, cause `{}`", e.getMessage());
            return "Something went wrong, the comment was not updated!";
        }
        return comment.get().toString();
    }

    @Transactional
    public String delete(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            return "Comment not found!";
        }
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Failed to update comment, cause `{}`", e.getMessage());
            return "Something went wrong, the comment was not deleted!";
        }
        return comment.get() + " deleted";
    }
}
