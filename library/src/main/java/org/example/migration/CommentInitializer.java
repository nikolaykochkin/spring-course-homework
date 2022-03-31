package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.model.Comment;
import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.List;

@ChangeUnit(id = "comment-initializer", order = "4", author = "mongock")
public class CommentInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookInitializer.class);
    private final ReactiveMongoTemplate template;

    public CommentInitializer(ReactiveMongoTemplate template) {
        this.template = template;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        template.createCollection("comments")
                .block();
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        template.dropCollection("comments")
                .block();
    }

    @Execution
    public void migrationMethod(CommentRepository repository, BookRepository bookRepository) {
        Flux.fromIterable(getComments())
                .doOnNext(comment -> LOGGER.info("{} 1", comment))
                .flatMap(comment -> bookRepository.findBookByTitle(comment.getBookId())
                        .doOnNext(book -> comment.setBookId(book.getId()))
                        .map(book -> comment))
                .doOnNext(comment -> LOGGER.info("{} 2", comment))
                .flatMap(repository::insert)
                .doOnNext(comment -> LOGGER.info("{} saved", comment))
                .blockLast();
    }

    @RollbackExecution
    public void rollback(CommentRepository repository) {
        repository.deleteAll().block();
    }

    private List<Comment> getComments() {
        return List.of(
                new Comment(
                        null,
                        "War and Peace",
                        null,
                        "Too long("
                ),
                new Comment(
                        null,
                        "Crime and Punishment",
                        null,
                        "Creepy"
                ),
                new Comment(
                        null,
                        "Crime and Punishment",
                        null,
                        "Amazing"
                )
        );
    }
}
