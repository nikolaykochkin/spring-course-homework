package org.example.shell;

import org.example.dao.AuthorDao;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AuthorShell {
    private final AuthorDao authorDao;

    public AuthorShell(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @ShellMethod(value = "Start", key = {"af", "authors find"})
    public String find(@ShellOption long id) {
        return authorDao.getById(id).toString();
    }
}
