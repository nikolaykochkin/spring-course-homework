package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.model.Genre;
import org.example.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

@ChangeUnit(id = "genre-initializer", order = "2", author = "mongock")
public class GenreInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreInitializer.class);
    private final ReactiveMongoTemplate template;

    public GenreInitializer(ReactiveMongoTemplate template) {
        this.template = template;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        template.createCollection("genres")
                .block();
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        template.dropCollection("genres")
                .block();
    }

    @Execution
    public void migrationMethod(GenreRepository repository) {
        Flux.just(
                        new Genre(null, "Novel"),
                        new Genre(null, "Poem")
                ).flatMap(repository::insert)
                .doOnNext(genre -> LOGGER.info("{} saved", genre))
                .blockLast();
    }

    @RollbackExecution
    public void rollback(GenreRepository repository) {
        repository.deleteAll()
                .block();
    }
}
