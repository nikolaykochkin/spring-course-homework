package org.example.service;

import org.example.dto.BookResponseDto;
import org.example.model.Book;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.example.util.ModelDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public Flux<BookResponseDto> getAll() {
        return bookRepository
                .getAllQueryDto()
                .map(ModelDtoUtil::bookQueryToResponseDto);
    }

    public Mono<BookResponseDto> findById(String id) {
        return bookRepository
                .findByIdQueryDto(id)
                .map(ModelDtoUtil::bookQueryToResponseDto);
    }

    public Mono<BookResponseDto> insert(Mono<Book> bookMono) {
        return bookMono
                .transform(getRelated())
                .flatMap(book -> Mono.just(book)
                        .zipWith(
                                bookRepository.insert(ModelDtoUtil.bookDtoToModel(book)),
                                (dto, b) -> {
                                    dto.setId(b.getId());
                                    return dto;
                                }
                        )
                );
    }

    public Mono<BookResponseDto> update(Mono<Book> bookMono, String id) {
        return bookMono
                .doOnNext(book -> book.setId(id))
                .filterWhen(book -> bookRepository.existsById(id))
                .transform(getRelated())
                .flatMap(book -> bookRepository.save(ModelDtoUtil.bookDtoToModel(book))
                        .map(b -> book));
    }

    public Mono<Void> delete(String id) {
        return bookRepository.deleteById(id);
    }

    private Function<Mono<Book>, Mono<BookResponseDto>> getRelated() {
        return mono -> mono
                .map(ModelDtoUtil::bookModelToDto)
                .flatMap(book -> Mono.just(book)
                        .zipWith(
                                authorRepository.findById(book.getAuthor().getId())
                                        .switchIfEmpty(Mono.error(new RuntimeException("Author not found"))),
                                (b, a) -> {
                                    b.setAuthor(a);
                                    return b;
                                }))
                .flatMap(book -> Mono.just(book)
                        .zipWith(
                                genreRepository.findById(book.getGenre().getId())
                                        .switchIfEmpty(Mono.error(new RuntimeException("Genre not found"))),
                                (b, g) -> {
                                    b.setGenre(g);
                                    return b;
                                }));
    }
}
