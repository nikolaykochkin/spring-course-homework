package org.example.shell;

import org.example.service.CommentService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CommentShell {
    private final CommentService commentService;

    public CommentShell(CommentService commentService) {
        this.commentService = commentService;
    }

    @ShellMethod(value = "Find comment by id", key = {"cf", "comment find"})
    public String find(@ShellOption long id) {
        return commentService.find(id);
    }

    @ShellMethod(value = "List of comments", key = {"cl", "comment list"})
    public String list() {
        return commentService.list();
    }

    @ShellMethod(value = "Create comment", key = {"cc", "comment create"})
    public String insert() {
        return commentService.insert();
    }

    @ShellMethod(value = "Update comment by id", key = {"cu", "comment update"})
    public String update(@ShellOption long id) {
        return commentService.update(id);
    }

    @ShellMethod(value = "Delete comment by id", key = {"cd", "comment delete"})
    public String delete(@ShellOption long id) {
        return commentService.delete(id);
    }
}
