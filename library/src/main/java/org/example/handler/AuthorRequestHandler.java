package org.example.handler;

import org.example.model.Author;
import org.example.service.AuthorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AuthorRequestHandler extends AbstractValidationHandler<Author> {
    private final AuthorService authorService;

    public AuthorRequestHandler(AuthorService authorService, Validator validator) {
        super(Author.class, validator);
        this.authorService = authorService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok().body(authorService.getAll(), Author.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return authorService.getAuthorById(request.pathVariable("id"))
                .flatMap(author -> ok().bodyValue(author))
                .switchIfEmpty(notFound().build());
    }

    @Override
    protected Mono<ServerResponse> processCreate(Author validBody, ServerRequest request) {
        return ok().body(authorService.create(Mono.just(validBody)), Author.class);
    }

    @Override
    protected Mono<ServerResponse> processUpdate(Author validBody, ServerRequest request) {
        return ok().body(authorService.update(Mono.just(validBody), request.pathVariable("id")), Author.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return ok().build(authorService.delete(request.pathVariable("id")));
    }
}
