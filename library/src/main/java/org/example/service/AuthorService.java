package org.example.service;

import org.example.dao.AuthorDao;
import org.example.exception.LibraryDataAccessException;
import org.example.model.Author;
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
public class AuthorService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorDao authorDao;
    private final Scanner scanner;
    private final PrintStream printStream;

    public AuthorService(AuthorDao authorDao,
                         @Value("#{T(java.lang.System).in}") InputStream inputStream,
                         @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.authorDao = authorDao;
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String find(long id) {
        Optional<Author> author = getAuthor(id);
        return author
                .map(Author::toString)
                .orElse("Author not found!");
    }

    private Optional<Author> getAuthor(long id) {
        Optional<Author> author = Optional.empty();
        try {
            author = Optional.of(authorDao.getById(id));
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to get author by id: `{}`, cause `{}`", id, e.getMessage());
        }
        return author;
    }

    public String list() {
        Optional<String> authors = Optional.empty();
        try {
            authors = Optional.of(authorDao.getAll().stream()
                    .map(Author::toString)
                    .collect(Collectors.joining("\n")));
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to get authors list, cause `{}`", e.getMessage());
        }

        if (authors.isPresent() && !authors.get().isBlank()) {
            return authors.get();
        } else {
            return "Authors not found!";
        }
    }

    public String insert() {
        printStream.print("Enter author name: ");
        Author author = new Author(scanner.nextLine());
        try {
            authorDao.insert(author);
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to save author, cause `{}`", e.getMessage());
            return "Something went wrong, the author was not saved!";
        }
        return author.toString();
    }

    public String update(long id) {
        Optional<Author> author = getAuthor(id);
        if (author.isEmpty()) {
            return "Author not found!";
        }

        printStream.println("Found author:");
        printStream.println(author.get());
        printStream.print("Enter new author name: ");
        author.get().setName(scanner.nextLine());

        try {
            authorDao.update(author.get());
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to update author, cause `{}`", e.getMessage());
            return "Something went wrong, the author was not updated!";
        }

        return author.get().toString();
    }

    public String delete(long id) {
        Optional<Author> author = getAuthor(id);
        if (author.isEmpty()) {
            return "Author not found!";
        }

        try {
            authorDao.deleteById(id);
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to update author, cause `{}`", e.getMessage());
            return "Something went wrong, the author was not deleted!";
        }

        return author.get() + " deleted";
    }
}
