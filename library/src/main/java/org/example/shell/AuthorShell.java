package org.example.shell;

import org.example.dao.AuthorDao;
import org.example.model.Author;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Scanner;
import java.util.stream.Collectors;

@ShellComponent
public class AuthorShell {
    private final AuthorDao authorDao;
    private final Scanner scanner;

    public AuthorShell(AuthorDao authorDao) {
        this.authorDao = authorDao;
        this.scanner = new Scanner(System.in);
    }

    @ShellMethod(value = "Find author by id", key = {"af", "authors find"})
    public String find(@ShellOption long id) {
        return authorDao.getById(id).toString();
    }

    @ShellMethod(value = "List of authors", key = {"al", "authors list"})
    public String list() {
        return authorDao.getAll().stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Create author", key = {"ac", "authors create"})
    public String insert() {
        System.out.print("Enter author name: ");
        Author author = new Author(scanner.nextLine());
        authorDao.insert(author);
        return author.toString();
    }

    @ShellMethod(value = "Delete author", key = {"ad", "authors delete"})
    public String delete(@ShellOption long id) {
        Author author = authorDao.getById(id);
        authorDao.deleteById(id);
        return "Author " + author + " deleted";
    }
}
