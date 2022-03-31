package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

@ChangeUnit(id = "author-initializer", order = "1", author = "mongock")
public class AuthorInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorInitializer.class);
    private final ReactiveMongoTemplate template;

    public AuthorInitializer(ReactiveMongoTemplate template) {
        this.template = template;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        template.createCollection("authors")
                .block();
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        template.dropCollection("authors")
                .block();
    }

    @Execution
    public void migrationMethod(AuthorRepository authorRepository) {
        Flux.just(
                        new Author(null, "Leo Tolstoy"),
                        new Author(null, "Fyodor Dostoevsky"),
                        new Author(null, "William Shakespeare")
                )
                .flatMap(authorRepository::insert)
                .doOnNext(author -> LOGGER.info("{} saved", author))
                .blockLast();
    }

    @RollbackExecution
    public void rollback(AuthorRepository authorRepository) {
        authorRepository.deleteAll()
                .block();
    }
}
