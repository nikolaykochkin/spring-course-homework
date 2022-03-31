package org.example.util;

import org.example.dto.BookQueryDto;
import org.example.dto.BookResponseDto;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

public class ModelDtoUtil {
    public static Book bookDtoToModel(BookResponseDto dto) {
        Book book = new Book();
        BeanUtils.copyProperties(dto, book);
        book.setAuthorId(dto.getAuthor().getId());
        book.setGenreId(dto.getGenre().getId());
        return book;
    }

    public static BookResponseDto bookModelToDto(Book book) {
        BookResponseDto dto = new BookResponseDto();
        BeanUtils.copyProperties(book, dto);
        dto.setAuthor(new Author(book.getAuthorId(), null));
        dto.setGenre(new Genre(book.getGenreId(), null));
        return dto;
    }

    public static BookResponseDto bookQueryToResponseDto(BookQueryDto bookQueryDto) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(bookQueryDto.getId());
        dto.setTitle(bookQueryDto.getTitle());
        dto.setAuthor(Optional.ofNullable(bookQueryDto.getAuthor())
                .map(authors -> authors.get(0))
                .orElse(null));
        dto.setGenre(Optional.ofNullable(bookQueryDto.getGenre())
                .map(genres -> genres.get(0))
                .orElse(null));
        return dto;
    }
}
