package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("comments")
public class Comment {
    @Id
    private String id;
    @NotNull(message = "Comment bookId should not be null")
    private String bookId;
    @CreatedDate
    private Date createdAt;
    @NotBlank(message = "Comment text should not be blank")
    private String text;
}
