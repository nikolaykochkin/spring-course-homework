package org.example.shell;

import org.example.service.AuthorService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AuthorShell {
    private final AuthorService authorService;

    public AuthorShell(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "Find author by name", key = {"af", "author find"})
    public String find(@ShellOption String name) {
        return authorService.find(name);
    }

    @ShellMethod(value = "List of authors", key = {"al", "author list"})
    public String list() {
        return authorService.list();
    }

    @ShellMethod(value = "Create author", key = {"ac", "author create"})
    public String insert() {
        return authorService.insert();
    }

    @ShellMethod(value = "Update author by name", key = {"au", "author update"})
    public String update(@ShellOption String name) {
        return authorService.update(name);
    }

    @ShellMethod(value = "Delete author by name", key = {"ad", "author delete"})
    public String delete(@ShellOption String name) {
        return authorService.delete(name);
    }
}
