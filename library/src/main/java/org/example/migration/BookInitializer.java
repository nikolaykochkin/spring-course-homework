package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.model.Book;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "book-initializer", order = "3", author = "mongock")
public class BookInitializer {
    private final MongoTemplate mongoTemplate;

    public BookInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection("books");
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection("books");
    }

    @Execution
    public void migrationMethod(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        repository.save(
                new Book(
                        null,
                        "War and Peace",
                        authorRepository.findAuthorByName("Leo Tolstoy").orElse(null),
                        genreRepository.findGenreByName("Novel").orElse(null)
                )
        );
        repository.save(
                new Book(
                        null,
                        "Crime and Punishment",
                        authorRepository.findAuthorByName("Fyodor Dostoevsky").orElse(null),
                        genreRepository.findGenreByName("Novel").orElse(null)
                )
        );
        repository.save(
                new Book(
                        null,
                        "Venus and Adonis",
                        authorRepository.findAuthorByName("William Shakespeare").orElse(null),
                        genreRepository.findGenreByName("Poem").orElse(null)
                )
        );
    }

    @RollbackExecution
    public void rollback(BookRepository repository) {
        repository.deleteAll();
    }
}
