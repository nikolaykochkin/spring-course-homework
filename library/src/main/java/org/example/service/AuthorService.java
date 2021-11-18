package org.example.service;

import org.example.model.Author;
import org.example.repository.AuthorRepository;
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

    private final AuthorRepository authorRepository;
    private final Scanner scanner;
    private final PrintStream printStream;

    public AuthorService(AuthorRepository authorRepository,
                         @Value("#{T(java.lang.System).in}") InputStream inputStream,
                         @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.authorRepository = authorRepository;
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String find(String name) {
        Optional<Author> authorOptional = Optional.empty();
        try {
            authorOptional = authorRepository.findAuthorByNameContains(name);
        } catch (Exception e) {
            LOGGER.error("Failed to find author by name `{}`, cause `{}`", name, e.getMessage());
        }
        return authorOptional
                .map(Author::toString)
                .orElse("Author not found!");
    }

    public String list() {
        Optional<String> authors = Optional.empty();
        try {
            authors = Optional.of(authorRepository.findAll().stream()
                    .map(Author::toString)
                    .collect(Collectors.joining("\n")));
        } catch (Exception e) {
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
        Author author = new Author();
        author.setName(scanner.nextLine());
        try {
            authorRepository.save(author);
        } catch (Exception e) {
            LOGGER.error("Failed to save author, cause `{}`", e.getMessage());
            return "Something went wrong, the author was not saved!";
        }
        return author.toString();
    }

    public String update(String name) {
        Optional<Author> author = authorRepository.findAuthorByNameContains(name);
        if (author.isEmpty()) {
            return "Author not found!";
        }

        printStream.println("Found author:");
        printStream.println(author.get());
        printStream.print("Enter new author name: ");
        author.get().setName(scanner.nextLine());

        try {
            authorRepository.save(author.get());
        } catch (Exception e) {
            LOGGER.error("Failed to update author, cause `{}`", e.getMessage());
            return "Something went wrong, the author was not updated!";
        }

        return author.get().toString();
    }

    public String delete(String name) {
        Optional<Author> author = authorRepository.findAuthorByNameContains(name);
        if (author.isEmpty()) {
            return "Author not found!";
        }

        try {
            authorRepository.deleteById(author.get().getId());
        } catch (Exception e) {
            LOGGER.error("Failed to update author, cause `{}`", e.getMessage());
            return "Something went wrong, the author was not deleted!";
        }

        return author.get() + " deleted";
    }
}
