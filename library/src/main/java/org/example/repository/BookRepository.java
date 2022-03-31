package org.example.repository;

import org.example.dto.BookQueryDto;
import org.example.model.Book;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findBookByTitle(String title);

    @Aggregation({
            "{\n" +
                    "    $addFields: {\n" +
                    "        'authorObjectId': {\n" +
                    "            $toObjectId: '$authorId'\n" +
                    "        },\n" +
                    "        'genreObjectId': {\n" +
                    "            $toObjectId: '$genreId'\n" +
                    "        }\n" +
                    "    }\n" +
                    "}",
            "{\n" +
                    "    $lookup: {\n" +
                    "        from: 'authors',\n" +
                    "        localField: 'authorObjectId',\n" +
                    "        foreignField: '_id',\n" +
                    "        as: 'author'\n" +
                    "    }\n" +
                    "}",
            "{\n" +
                    "    $lookup: {\n" +
                    "        from: 'genres',\n" +
                    "        localField: 'genreObjectId',\n" +
                    "        foreignField: '_id',\n" +
                    "        as: 'genre'\n" +
                    "    }\n" +
                    "}"
    })
    Flux<BookQueryDto> getAllQueryDto();

    @Aggregation({
            "{\n" +
                    "    $match: {\n" +
                    "        _id: ObjectId(?0)\n" +
                    "    }\n" +
                    "}",
            "{\n" +
                    "    $addFields: {\n" +
                    "        'authorObjectId': {\n" +
                    "            $toObjectId: '$authorId'\n" +
                    "        },\n" +
                    "        'genreObjectId': {\n" +
                    "            $toObjectId: '$genreId'\n" +
                    "        }\n" +
                    "    }\n" +
                    "}",
            "{\n" +
                    "    $lookup: {\n" +
                    "        from: 'authors',\n" +
                    "        localField: 'authorObjectId',\n" +
                    "        foreignField: '_id',\n" +
                    "        as: 'author'\n" +
                    "    }\n" +
                    "}",
            "{\n" +
                    "    $lookup: {\n" +
                    "        from: 'genres',\n" +
                    "        localField: 'genreObjectId',\n" +
                    "        foreignField: '_id',\n" +
                    "        as: 'genre'\n" +
                    "    }\n" +
                    "}"
    })
    Mono<BookQueryDto> findByIdQueryDto(String bookId);
}
