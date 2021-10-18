package org.example.service;

import org.example.dao.GenreDao;
import org.example.exception.LibraryDataAccessException;
import org.example.model.Genre;
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
public class GenreService {
    private final static Logger LOGGER = LoggerFactory.getLogger(GenreService.class);

    private final GenreDao genreDao;
    private final Scanner scanner;
    private final PrintStream printStream;

    public GenreService(GenreDao genreDao,
                        @Value("#{T(java.lang.System).in}") InputStream inputStream,
                        @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.genreDao = genreDao;
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String find(long id) {
        return genreDao.getByIdOptional(id)
                .map(Genre::toString)
                .orElse("Genre not found!");
    }

    public String list() {
        Optional<String> genres = Optional.empty();
        try {
            genres = Optional.of(genreDao.getAll().stream()
                    .map(Genre::toString)
                    .collect(Collectors.joining("\n")));
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to get genres list, cause `{}`", e.getMessage());
        }
        if (genres.isPresent() && !genres.get().isBlank()) {
            return genres.get();
        } else {
            return "Genres not found!";
        }
    }

    public String insert() {
        printStream.print("Enter genre name: ");
        Genre genre = new Genre(scanner.nextLine());
        try {
            genreDao.insert(genre);
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to save genre, cause `{}`", e.getMessage());
            return "Something went wrong, the genre was not saved!";
        }
        return genre.toString();
    }

    public String update(long id) {
        Optional<Genre> genre = genreDao.getByIdOptional(id);
        if (genre.isEmpty()) {
            return "Genre not found!";
        }
        printStream.println("Found genre:");
        printStream.println(genre);
        printStream.print("Enter new genre name: ");
        genre.get().setName(scanner.nextLine());
        try {
            genreDao.update(genre.get());
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to update genre, cause `{}`", e.getMessage());
            return "Something went wrong, the genre was not updated!";
        }
        return genre.get().toString();
    }

    public String delete(long id) {
        Optional<Genre> genre = genreDao.getByIdOptional(id);
        if (genre.isEmpty()) {
            return "Genre not found!";
        }
        try {
            genreDao.deleteById(id);
        } catch (LibraryDataAccessException e) {
            LOGGER.error("Failed to update genre, cause `{}`", e.getMessage());
            return "Something went wrong, the genre was not deleted!";
        }
        return genre + " deleted";
    }
}
