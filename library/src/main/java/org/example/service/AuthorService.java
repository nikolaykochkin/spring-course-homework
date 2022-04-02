package org.example.service;

import org.bson.types.ObjectId;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Mono<Author> getAuthorById(String id) {
        return Mono.just(id)
                .filter(ObjectId::isValid)
                .flatMap(authorRepository::findById);
    }

    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }

    public Mono<Author> save(Mono<Author> authorMono) {
        return authorMono.flatMap(authorRepository::insert);
    }

    public Mono<Author> update(Mono<Author> authorMono, String id) {
        return authorMono
                .filter(author -> ObjectId.isValid(id))
                .filterWhen(author -> authorRepository.existsById(id))
                .doOnNext(author -> author.setId(id))
                .flatMap(authorRepository::save);
    }

    public Mono<Void> delete(String id) {
        return authorRepository.deleteById(id);
    }
}
