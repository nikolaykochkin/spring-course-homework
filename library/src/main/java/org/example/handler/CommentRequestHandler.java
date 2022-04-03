package org.example.handler;

import org.example.model.Comment;
import org.example.service.CommentService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CommentRequestHandler extends AbstractValidationHandler<Comment> {
    private final CommentService commentService;

    public CommentRequestHandler(CommentService commentService, Validator validator) {
        super(Comment.class, validator);
        this.commentService = commentService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return request.queryParam("bookId")
                .map(id -> ok().body(commentService.getByBookId(id), Comment.class))
                .orElseGet(() -> ok().body(commentService.getAll(), Comment.class));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return commentService.getCommentById(request.pathVariable("id"))
                .flatMap(comment -> ok().bodyValue(comment))
                .switchIfEmpty(notFound().build());
    }

    @Override
    protected Mono<ServerResponse> processCreate(Comment validBody, ServerRequest request) {
        return ok().body(commentService.create(Mono.just(validBody)), Comment.class);
    }

    @Override
    protected Mono<ServerResponse> processUpdate(Comment validBody, ServerRequest request) {
        return ok().body(commentService.update(Mono.just(validBody), request.pathVariable("id")), Comment.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return ok().build(commentService.delete(request.pathVariable("id")));
    }
}
