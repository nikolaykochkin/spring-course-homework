package org.example.controller;

import org.example.dto.BookResponseDto;
import org.example.model.Book;
import org.example.service.BookService;
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
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BookResponseDto>> getBookById(@PathVariable("id") String id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<BookResponseDto> findAll() {
        return bookService.getAll();
    }

    @PostMapping
    public Mono<BookResponseDto> save(@Valid @RequestBody Mono<Book> book) {
        return bookService.insert(book);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BookResponseDto>> update(@Valid @RequestBody Mono<Book> book, @PathVariable("id") String id) {
        return bookService.update(book, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return bookService.delete(id);
    }
}
