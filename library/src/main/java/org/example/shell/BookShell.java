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

    @ShellMethod(value = "Find book by title", key = {"bf", "book find"})
    public String find(@ShellOption String title) {
        return bookService.find(title);
    }

    @ShellMethod(value = "List of books", key = {"bl", "book list"})
    public String list() {
        return bookService.list();
    }

    @ShellMethod(value = "Create book", key = {"bc", "book create"})
    public String insert() {
        return bookService.insert();
    }

    @ShellMethod(value = "Update book by title", key = {"bu", "book update"})
    public String update(@ShellOption String title) {
        return bookService.update(title);
    }

    @ShellMethod(value = "Delete book by title", key = {"bd", "book delete"})
    public String delete(@ShellOption String title) {
        return bookService.delete(title);
    }

}
