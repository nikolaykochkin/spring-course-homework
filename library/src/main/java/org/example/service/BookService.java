package org.example.service;

import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public String find(String title) {
        Optional<Book> optionalBook = Optional.empty();
        try {
            optionalBook = bookRepository.findBookByTitleContains(title);
        } catch (Exception e) {
            LOGGER.error("Failed to find book by title `{}`, cause `{}`", title, e.getMessage());
        }
        return optionalBook
                .map(Book::toString)
                .orElse("Book not found!");
    }

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

    public String insert() {
        printStream.print("Enter book title: ");
        String title = scanner.nextLine();
        printStream.print("Enter author name: ");
        Optional<Author> author = authorRepository.findAuthorByNameContains(scanner.nextLine());
        if (author.isEmpty()) {
            return "Author not found!";
        }
        printStream.print("Enter genre name: ");
        Optional<Genre> genre = genreRepository.findGenreByNameContains(scanner.nextLine());
        if (genre.isEmpty()) {
            return "Genre not found!";
        }
        Book book = new Book(null, title, author.get(), genre.get());
        try {
            bookRepository.save(book);
        } catch (Exception e) {
            LOGGER.error("Failed to save book, cause `{}`", e.getMessage());
            return "Something went wrong, the book was not saved!";
        }
        return book.toString();
    }

    public String update(String title) {
        Optional<Book> book = bookRepository.findBookByTitleContains(title);
        if (book.isEmpty()) {
            return "Book not found!";
        }
        printStream.println("Found book:");
        printStream.println(book);
        printStream.print("Enter new book title: ");
        book.get().setTitle(scanner.nextLine());
        printStream.print("Enter author name: ");
        Optional<Author> author = authorRepository.findAuthorByNameContains(scanner.nextLine());
        if (author.isEmpty()) {
            return "Author not found!";
        }
        book.get().setAuthor(author.get());
        printStream.print("Enter genre name: ");
        Optional<Genre> genre = genreRepository.findGenreByNameContains(scanner.nextLine());
        if (genre.isEmpty()) {
            return "Genre not found!";
        }
        book.get().setGenre(genre.get());
        try {
            bookRepository.save(book.get());
        } catch (Exception e) {
            LOGGER.error("Failed to update book, cause `{}`", e.getMessage());
            return "Something went wrong, the book was not updated!";
        }
        return book.get().toString();
    }

    public String delete(String title) {
        Optional<Book> book = bookRepository.findBookByTitleContains(title);
        if (book.isEmpty()) {
            return "Book not found!";
        }
        try {
            bookRepository.deleteById(book.get().getId());
        } catch (Exception e) {
            LOGGER.error("Failed to update book, cause `{}`", e.getMessage());
            return "Something went wrong, the book was not deleted!";
        }

        return book + " deleted";
    }
}
