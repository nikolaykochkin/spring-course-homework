package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.model.Comment;
import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "comment-initializer", order = "4", author = "mongock")
public class CommentInitializer {
    private final MongoTemplate mongoTemplate;

    public CommentInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection("comments");
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection("comments");
    }

    @Execution
    public void migrationMethod(CommentRepository repository, BookRepository bookRepository) {
        Comment comment1 = new Comment();
        comment1.setBook(bookRepository.findBookByTitle("War and Peace").orElse(null));
        comment1.setText("Too long(");
        repository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setBook(bookRepository.findBookByTitle("Crime and Punishment").orElse(null));
        comment2.setText("Creepy");
        repository.save(comment2);

        Comment comment3 = new Comment();
        comment3.setBook(bookRepository.findBookByTitle("Crime and Punishment").orElse(null));
        comment3.setText("Amazing");
        repository.save(comment3);
    }

    @RollbackExecution
    public void rollback(CommentRepository repository) {
        repository.deleteAll();
    }
}
