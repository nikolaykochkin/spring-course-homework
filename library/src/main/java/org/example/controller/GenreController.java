package org.example.controller;

import org.example.model.Genre;
import org.example.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Genre>> getGenreById(@PathVariable("id") String id) {
        return genreService.getGenreById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Genre> findAll() {
        return genreService.findAll();
    }

    @PostMapping
    public Mono<Genre> save(@Valid @RequestBody Mono<Genre> genre) {
        return genreService.save(genre);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Genre>> update(@Valid @RequestBody Mono<Genre> genre, @PathVariable("id") String id) {
        return genreService.update(genre, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return genreService.delete(id);
    }
}
