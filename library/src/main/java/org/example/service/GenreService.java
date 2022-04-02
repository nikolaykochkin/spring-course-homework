package org.example.service;

import org.bson.types.ObjectId;
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
        return Mono.just(id)
                .filter(ObjectId::isValid)
                .flatMap(genreRepository::findById);
    }

    public Flux<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Mono<Genre> save(Mono<Genre> genreMono) {
        return genreMono.flatMap(genreRepository::insert);
    }

    public Mono<Genre> update(Mono<Genre> genreMono, String id) {
        return genreMono
                .filter(genre -> ObjectId.isValid(id))
                .filterWhen(genre -> genreRepository.existsById(id))
                .doOnNext(genre -> genre.setId(id))
                .flatMap(genreRepository::save);
    }

    public Mono<Void> delete(String id) {
        return genreRepository.deleteById(id);
    }
}
