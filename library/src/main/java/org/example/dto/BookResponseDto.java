package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Author;
import org.example.model.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private String id;
    private String title;
    private Author author;
    private Genre genre;
}
