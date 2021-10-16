package org.example.service;

import org.example.dao.AuthorDao;
import org.example.dao.BookDao;
import org.example.dao.GenreDao;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final Scanner scanner;
    private final PrintStream printStream;

    public BookService(BookDao bookDao,
                       AuthorDao authorDao,
                       GenreDao genreDao,
                       @Value("#{T(java.lang.System).in}") InputStream inputStream,
                       @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String find(long id) {
        return bookDao.getById(id).toString();
    }

    public String list() {
        return bookDao.getAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    public String insert() {
        printStream.print("Enter book name: ");
        String name = scanner.nextLine();
        printStream.print("Enter author id: ");
        Author author = authorDao.getById(Long.parseLong(scanner.nextLine()));
        printStream.print("Enter genre id: ");
        Genre genre = genreDao.getById(Long.parseLong(scanner.nextLine()));
        Book book = new Book(name, author, genre);
        bookDao.insert(book);
        return book.toString();
    }

    public String update(long id) {
        Book book = bookDao.getById(id);
        printStream.println("Found book:");
        printStream.println(book);
        printStream.print("Enter new book name: ");
        book.setName(scanner.nextLine());
        printStream.print("Enter new author id: ");
        book.setAuthor(authorDao.getById(Long.parseLong(scanner.nextLine())));
        printStream.print("Enter new genre id: ");
        book.setGenre(genreDao.getById(Long.parseLong(scanner.nextLine())));
        bookDao.update(book);
        return bookDao.getById(id).toString();
    }

    public String delete(long id) {
        Book book = bookDao.getById(id);
        bookDao.deleteById(id);
        return "Book " + book + " deleted";
    }
}
