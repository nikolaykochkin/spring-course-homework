package org.example.service;

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
        return authorRepository.findById(id);
    }

    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }

    public Mono<Author> save(Mono<Author> author) {
        return author.flatMap(authorRepository::insert);
    }

    public Mono<Author> update(Mono<Author> author, String id) {
        return author
                .flatMap(a -> authorRepository.findById(id)
                        .map(author1 -> a))
                .doOnNext(a -> a.setId(id))
                .flatMap(authorRepository::save);
    }

    public Mono<Void> delete(String id) {
        return authorRepository.deleteById(id);
    }
}
