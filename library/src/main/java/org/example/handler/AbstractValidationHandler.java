package org.example.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class AbstractValidationHandler<T> {
    private final Class<T> validationClass;
    private final Validator validator;

    protected AbstractValidationHandler(Class<T> validationClass, Validator validator) {
        this.validationClass = validationClass;
        this.validator = validator;
    }

    abstract protected Mono<ServerResponse> processCreate(T validBody, ServerRequest request);

    abstract protected Mono<ServerResponse> processUpdate(T validBody, ServerRequest request);

    public final Mono<ServerResponse> create(ServerRequest request) {
        return validate(request, false);
    }

    public final Mono<ServerResponse> update(ServerRequest request) {
        return validate(request, true);
    }

    private Mono<ServerResponse> validate(ServerRequest request, boolean update) {
        return request.bodyToMono(validationClass)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, validationClass.getName());
                    validator.validate(body, errors);
                    if (errors.hasErrors()) {
                        return onValidationErrors(errors);
                    } else {
                        return update ? processUpdate(body, request) : processCreate(body, request);
                    }
                });
    }

    protected Mono<ServerResponse> onValidationErrors(Errors errors) {
        return ServerResponse.badRequest()
                .bodyValue(errors.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage));
    }
}
