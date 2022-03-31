package org.example.migration;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.example.dto.BookResponseDto;
import org.example.model.Author;
import org.example.model.Genre;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.example.util.ModelDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.List;

@ChangeUnit(id = "book-initializer", order = "3", author = "mongock")
public class BookInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookInitializer.class);
    private final ReactiveMongoTemplate template;

    public BookInitializer(ReactiveMongoTemplate template) {
        this.template = template;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        template.createCollection("books")
                .block();
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {
        template.dropCollection("books")
                .block();
    }

    @Execution
    public void migrationMethod(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        Flux.fromIterable(getBooks())
                .flatMap(book -> authorRepository.findAuthorByName(book.getAuthor().getName())
                        .doOnNext(book::setAuthor)
                        .map(author -> book))
                .flatMap(book -> genreRepository.findGenreByName(book.getGenre().getName())
                        .doOnNext(book::setGenre)
                        .map(author -> book))
                .map(ModelDtoUtil::bookDtoToModel)
                .flatMap(repository::insert)
                .doOnNext(book -> LOGGER.info("{} saved", book))
                .blockLast();
    }

    @RollbackExecution
    public void rollback(BookRepository repository) {
        repository.deleteAll().block();
    }

    private List<BookResponseDto> getBooks() {
        return List.of(
                new BookResponseDto(
                        null,
                        "War and Peace",
                        new Author(null, "Leo Tolstoy"),
                        new Genre(null, "Novel")
                ),
                new BookResponseDto(
                        null,
                        "Crime and Punishment",
                        new Author(null, "Fyodor Dostoevsky"),
                        new Genre(null, "Novel")
                ),
                new BookResponseDto(
                        null,
                        "Venus and Adonis",
                        new Author(null, "William Shakespeare"),
                        new Genre(null, "Poem")
                )
        );
    }
}
