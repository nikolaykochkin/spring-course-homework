package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Author;
import org.example.model.Genre;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookQueryDto {
    private String id;
    private String title;
    private List<Author> author;
    private List<Genre> genre;
}
