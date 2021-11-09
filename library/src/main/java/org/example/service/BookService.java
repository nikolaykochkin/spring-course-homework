package org.example.service;

import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final Scanner scanner;
    private final PrintStream printStream;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       GenreRepository genreRepository,
                       @Value("#{T(java.lang.System).in}") InputStream inputStream,
                       @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    @Transactional(readOnly = true)
    public String find(long id) {
        Optional<Book> optionalBook = Optional.empty();
        try {
            optionalBook = bookRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error("Failed to find book by id `{}`, cause `{}`", id, e.getMessage());
        }
        return optionalBook
                .map(Book::toString)
                .orElse("Book not found!");
    }

    @Transactional(readOnly = true)
    public String list() {
        Optional<String> books = Optional.empty();
        try {
            books = Optional.of(bookRepository.findAll().stream()
                    .map(Book::toString)
                    .collect(Collectors.joining("\n")));
        } catch (Exception e) {
            LOGGER.error("Failed to get authors list, cause `{}`", e.getMessage());
        }
        if (books.isPresent() && !books.get().isBlank()) {
            return books.get();
        } else {
            return "Books not found!";
        }
    }

    @Transactional
    public String insert() {
        printStream.print("Enter book name: ");
        String name = scanner.nextLine();
        printStream.print("Enter author id: ");
        Optional<Author> author = authorRepository.findById(Long.parseLong(scanner.nextLine()));
        if (author.isEmpty()) {
            return "Author not found!";
        }
        printStream.print("Enter genre id: ");
        Optional<Genre> genre = genreRepository.findById(Long.parseLong(scanner.nextLine()));
        if (genre.isEmpty()) {
            return "Genre not found!";
        }
        Book book = new Book(0, name, author.get(), genre.get(), null);
        try {
            bookRepository.save(book);
        } catch (Exception e) {
            LOGGER.error("Failed to save book, cause `{}`", e.getMessage());
            return "Something went wrong, the book was not saved!";
        }
        return book.toString();
    }

    @Transactional
    public String update(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return "Book not found!";
        }
        printStream.println("Found book:");
        printStream.println(book);
        printStream.print("Enter new book name: ");
        book.get().setName(scanner.nextLine());
        printStream.print("Enter new author id: ");
        Optional<Author> author = authorRepository.findById(Long.parseLong(scanner.nextLine()));
        if (author.isEmpty()) {
            return "Author not found!";
        }
        book.get().setAuthor(author.get());
        printStream.print("Enter new genre id: ");
        Optional<Genre> genre = genreRepository.findById(Long.parseLong(scanner.nextLine()));
        if (genre.isEmpty()) {
            return "Genre not found!";
        }
        try {
            bookRepository.save(book.get());
        } catch (Exception e) {
            LOGGER.error("Failed to update book, cause `{}`", e.getMessage());
            return "Something went wrong, the book was not updated!";
        }
        return book.get().toString();
    }

    @Transactional
    public String delete(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return "Book not found!";
        }
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Failed to update book, cause `{}`", e.getMessage());
            return "Something went wrong, the book was not deleted!";
        }

        return book + " deleted";
    }
}
