package org.example.service;

import org.example.model.Genre;
import org.example.repository.GenreRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Mono<Genre> getGenreById(String id) {
        return genreRepository.findById(id);
    }

    public Flux<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Mono<Genre> save(Mono<Genre> genre) {
        return genre.flatMap(genreRepository::insert);
    }

    public Mono<Genre> update(Mono<Genre> genre, String id) {
        return genre
                .flatMap(g -> genreRepository.findById(id)
                        .map(genre1 -> g))
                .doOnNext(g -> g.setId(id))
                .flatMap(genreRepository::save);
    }

    public Mono<Void> delete(String id) {
        return genreRepository.deleteById(id);
    }
}
