package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.model.Genre;
import org.example.repository.GenreRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "genre-initializer", order = "2", author = "mongock")
public class GenreInitializer {
    private final MongoTemplate mongoTemplate;

    public GenreInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection("genres");
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection("genres");
    }

    @Execution
    public void migrationMethod(GenreRepository repository) {
        repository.save(new Genre(null, "Novel"));
        repository.save(new Genre(null, "Poem"));
    }

    @RollbackExecution
    public void rollback(GenreRepository repository) {
        repository.deleteAll();
    }
}
