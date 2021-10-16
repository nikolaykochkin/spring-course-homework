package org.example.shell;

import org.example.service.BookService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class BookShell {
    private final BookService bookService;

    public BookShell(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Find book by id", key = {"bf", "book find"})
    public String find(@ShellOption long id) {
        return bookService.find(id);
    }

    @ShellMethod(value = "List of books", key = {"bl", "book list"})
    public String list() {
        return bookService.list();
    }

    @ShellMethod(value = "Create book", key = {"bc", "book create"})
    public String insert() {
        return bookService.insert();
    }

    @ShellMethod(value = "Update book by id", key = {"bu", "book update"})
    public String update(@ShellOption long id) {
        return bookService.update(id);
    }

    @ShellMethod(value = "Delete book", key = {"bd", "book delete"})
    public String delete(@ShellOption long id) {
        return bookService.delete(id);
    }

}
