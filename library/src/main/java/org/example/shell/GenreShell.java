package org.example.shell;

import org.example.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class GenreShell {
    private final GenreService genreService;

    public GenreShell(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod(value = "Find genre by id", key = {"gf", "genre find"})
    public String find(@ShellOption long id) {
        return genreService.find(id);
    }

    @ShellMethod(value = "List of genres", key = {"gl", "genre list"})
    public String list() {
        return genreService.list();
    }

    @ShellMethod(value = "Create genre", key = {"gc", "genre create"})
    public String insert() {
        return genreService.insert();
    }

    @ShellMethod(value = "Update genre by id", key = {"gu", "genre update"})
    public String update(@ShellOption long id) {
        return genreService.update(id);
    }

    @ShellMethod(value = "Delete genre by id", key = {"gd", "genre delete"})
    public String delete(@ShellOption long id) {
        return genreService.delete(id);
    }
}
