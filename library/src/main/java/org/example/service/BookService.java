package org.example.service;

import org.bson.types.ObjectId;
import org.example.dto.BookResponseDto;
import org.example.exception.RelatedNotFoundException;
import org.example.model.Book;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.example.repository.GenreRepository;
import org.example.util.ModelDtoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       GenreRepository genreRepository,
                       CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    public Flux<BookResponseDto> getAll() {
        return bookRepository
                .getAllQueryDto()
                .map(ModelDtoUtil::bookQueryToResponseDto);
    }

    public Mono<BookResponseDto> getById(String id) {
        return Mono.just(id)
                .filter(ObjectId::isValid)
                .flatMap(bookRepository::findByIdQueryDto)
                .map(ModelDtoUtil::bookQueryToResponseDto);
    }

    public Mono<BookResponseDto> create(Mono<Book> bookMono) {
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
                .filter(book -> ObjectId.isValid(id))
                .doOnNext(book -> book.setId(id))
                .filterWhen(book -> bookRepository.existsById(id))
                .transform(getRelated())
                .flatMap(book -> bookRepository.save(ModelDtoUtil.bookDtoToModel(book))
                        .map(b -> book));
    }

    @Transactional
    public Mono<Void> delete(String id) {
        return bookRepository.findById(id)
                .flatMap(book -> commentRepository.deleteAll(commentRepository.findCommentsByBookId(book.getId())))
                .then(bookRepository.deleteById(id));
    }

    private Function<Mono<Book>, Mono<BookResponseDto>> getRelated() {
        return mono -> mono
                .map(ModelDtoUtil::bookModelToDto)
                .flatMap(book -> Mono.just(book)
                        .zipWith(
                                authorRepository.findById(book.getAuthor().getId())
                                        .switchIfEmpty(Mono.error(new RelatedNotFoundException("Author not found"))),
                                (b, a) -> {
                                    b.setAuthor(a);
                                    return b;
                                }))
                .flatMap(book -> Mono.just(book)
                        .zipWith(
                                genreRepository.findById(book.getGenre().getId())
                                        .switchIfEmpty(Mono.error(new RelatedNotFoundException("Genre not found"))),
                                (b, g) -> {
                                    b.setGenre(g);
                                    return b;
                                }));
    }
}
