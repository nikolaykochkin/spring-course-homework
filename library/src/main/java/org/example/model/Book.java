package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("books")
public class Book {
    @Id
    private String id;
    @NotBlank(message = "Book title should not be blank")
    private String title;
    @NotNull(message = "Book authorId should not be null")
    private String authorId;
    @NotNull(message = "Book genreId should not be null")
    private String genreId;
}
