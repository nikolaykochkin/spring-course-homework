package org.example.service;

import org.example.dao.GenreDao;
import org.example.model.Genre;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class GenreService {
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
        return genreDao.getById(id).toString();
    }

    public String list() {
        return genreDao.getAll().stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }

    public String insert() {
        printStream.print("Enter genre name: ");
        Genre genre = new Genre(scanner.nextLine());
        genreDao.insert(genre);
        return genre.toString();
    }

    public String update(long id) {
        Genre genre = genreDao.getById(id);
        printStream.println("Found genre:");
        printStream.println(genre);
        printStream.print("Enter new genre name: ");
        genre.setName(scanner.nextLine());
        genreDao.update(genre);
        return genreDao.getById(id).toString();
    }

    public String delete(long id) {
        Genre genre = genreDao.getById(id);
        genreDao.deleteById(id);
        return "Genre " + genre + " deleted";
    }
}
