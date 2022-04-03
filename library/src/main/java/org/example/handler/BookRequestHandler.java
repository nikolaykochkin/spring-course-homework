package org.example.handler;

import org.example.model.Book;
import org.example.service.BookService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookRequestHandler extends AbstractValidationHandler<Book> {
    private final BookService bookService;

    public BookRequestHandler(BookService bookService, Validator validator) {
        super(Book.class, validator);
        this.bookService = bookService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok().body(bookService.getAll(), Book.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return bookService.getById(request.pathVariable("id"))
                .flatMap(book -> ok().bodyValue(book))
                .switchIfEmpty(notFound().build());
    }

    @Override
    protected Mono<ServerResponse> processCreate(Book validBody, ServerRequest request) {
        return ok().body(bookService.create(Mono.just(validBody)), Book.class);
    }

    @Override
    protected Mono<ServerResponse> processUpdate(Book validBody, ServerRequest request) {
        return ok().body(bookService.update(Mono.just(validBody), request.pathVariable("id")), Book.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return ok().build(bookService.delete(request.pathVariable("id")));
    }
}
