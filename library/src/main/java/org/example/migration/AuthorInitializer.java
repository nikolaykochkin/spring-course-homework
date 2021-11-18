package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "author-initializer", order = "1", author = "mongock")
public class AuthorInitializer {
    private final MongoTemplate mongoTemplate;

    public AuthorInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection("authors");
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection("authors");
    }

    @Execution
    public void migrationMethod(AuthorRepository authorRepository) {
        authorRepository.save(new Author(null, "Leo Tolstoy"));
        authorRepository.save(new Author(null, "Fyodor Dostoevsky"));
        authorRepository.save(new Author(null, "William Shakespeare"));
    }

    @RollbackExecution
    public void rollback(AuthorRepository authorRepository) {
        authorRepository.deleteAll();
    }

}
