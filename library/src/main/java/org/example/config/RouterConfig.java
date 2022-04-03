package org.example.config;

import org.example.exception.RelatedNotFoundException;
import org.example.handler.AuthorRequestHandler;
import org.example.handler.BookRequestHandler;
import org.example.handler.CommentRequestHandler;
import org.example.handler.GenreRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {
    private final AuthorRequestHandler authorRequestHandler;
    private final GenreRequestHandler genreRequestHandler;
    private final BookRequestHandler bookRequestHandler;
    private final CommentRequestHandler commentRequestHandler;

    public RouterConfig(AuthorRequestHandler authorRequestHandler,
                        GenreRequestHandler genreRequestHandler,
                        BookRequestHandler bookRequestHandler,
                        CommentRequestHandler commentRequestHandler) {
        this.authorRequestHandler = authorRequestHandler;
        this.genreRequestHandler = genreRequestHandler;
        this.bookRequestHandler = bookRequestHandler;
        this.commentRequestHandler = commentRequestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("api/authors", this::authorsRouterFunction)
                .path("api/genres", this::genresRouterFunction)
                .path("api/books", this::booksRouterFunction)
                .path("api/comments", this::commentsRouterFunction)
                .onError(RelatedNotFoundException.class, exceptionHandler())
                .build();
    }

    private RouterFunction<ServerResponse> authorsRouterFunction() {
        return RouterFunctions.route()
                .GET("", authorRequestHandler::getAll)
                .GET("{id}", authorRequestHandler::getById)
                .POST("", authorRequestHandler::create)
                .PUT("{id}", authorRequestHandler::update)
                .DELETE("{id}", authorRequestHandler::delete)
                .build();
    }

    private RouterFunction<ServerResponse> genresRouterFunction() {
        return RouterFunctions.route()
                .GET("", genreRequestHandler::getAll)
                .GET("{id}", genreRequestHandler::getById)
                .POST("", genreRequestHandler::create)
                .PUT("{id}", genreRequestHandler::update)
                .DELETE("{id}", genreRequestHandler::delete)
                .build();
    }

    private RouterFunction<ServerResponse> booksRouterFunction() {
        return RouterFunctions.route()
                .GET("", bookRequestHandler::getAll)
                .GET("{id}", bookRequestHandler::getById)
                .POST("", bookRequestHandler::create)
                .PUT("{id}", bookRequestHandler::update)
                .DELETE("{id}", bookRequestHandler::delete)
                .build();
    }

    private RouterFunction<ServerResponse> commentsRouterFunction() {
        return RouterFunctions.route()
                .GET("", commentRequestHandler::getAll)
                .GET("{id}", commentRequestHandler::getById)
                .POST("", commentRequestHandler::create)
                .PUT("{id}", commentRequestHandler::update)
                .DELETE("{id}", commentRequestHandler::delete)
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (throwable, serverRequest) -> ServerResponse.badRequest().bodyValue(throwable.getMessage());
    }
}
