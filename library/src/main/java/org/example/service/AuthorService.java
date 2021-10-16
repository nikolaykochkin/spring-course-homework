package org.example.service;

import org.example.dao.AuthorDao;
import org.example.model.Author;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorDao authorDao;
    private final Scanner scanner;
    private final PrintStream printStream;

    public AuthorService(AuthorDao authorDao,
                         @Value("#{T(java.lang.System).in}") InputStream inputStream,
                         @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.authorDao = authorDao;
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String find(long id) {
        return authorDao.getById(id).toString();
    }

    public String list() {
        return authorDao.getAll().stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }

    public String insert() {
        printStream.print("Enter author name: ");
        Author author = new Author(scanner.nextLine());
        authorDao.insert(author);
        return author.toString();
    }

    public String update(long id) {
        Author author = authorDao.getById(id);
        printStream.println("Found author:");
        printStream.println(author);
        printStream.print("Enter new author name: ");
        author.setName(scanner.nextLine());
        authorDao.update(author);
        return authorDao.getById(id).toString();
    }

    public String delete(long id) {
        Author author = authorDao.getById(id);
        authorDao.deleteById(id);
        return "Author " + author + " deleted";
    }
}
