package org.example.handler;

import org.example.model.Genre;
import org.example.service.GenreService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GenreRequestHandler extends AbstractValidationHandler<Genre> {
    private final GenreService genreService;

    public GenreRequestHandler(GenreService genreService, Validator validator) {
        super(Genre.class, validator);
        this.genreService = genreService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok().body(genreService.getAll(), Genre.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return genreService.getGenreById(request.pathVariable("id"))
                .flatMap(genre -> ok().bodyValue(genre))
                .switchIfEmpty(notFound().build());
    }

    @Override
    protected Mono<ServerResponse> processCreate(Genre validBody, ServerRequest request) {
        return ok().body(genreService.create(Mono.just(validBody)), Genre.class);
    }

    @Override
    protected Mono<ServerResponse> processUpdate(Genre validBody, ServerRequest request) {
        return ok().body(genreService.update(Mono.just(validBody), request.pathVariable("id")), Genre.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return ok().build(genreService.delete(request.pathVariable("id")));
    }
}
