package org.example.dao;

import org.example.domain.Quiz;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuizDaoCsvTest {
    private static final String TEST_QUIZZES_FILENAME = "test_quizzes.csv";

    @Test
    void should_ReturnAllQuizzes_When_PassCorrectFilename() {
        QuizDaoCsv quizDaoCsv = new QuizDaoCsv(TEST_QUIZZES_FILENAME);
        List<Quiz> expected = new ArrayList<>();
        expected.add(new Quiz(
                "question1",
                List.of("answer1", "answer2", "answer3", "answer4"),
                Collections.singleton(1)
        ));
        expected.add(new Quiz(
                "question2",
                List.of("answer1", "answer2", "answer3", "answer4"),
                Collections.singleton(2)
        ));

        List<Quiz> actual = quizDaoCsv.getAllQuizzes();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_ThrowIllegalArgumentException_When_PassWrongFilename() {
        Executable executable = () -> new QuizDaoCsv("abc");
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void should_ThrowIllegalArgumentException_When_PassEmptyFilename() {
        Executable executable = () -> {
            QuizDaoCsv quizDaoCsv = new QuizDaoCsv("");
            quizDaoCsv.getAllQuizzes();
        };
        assertThrows(IllegalArgumentException.class, executable);
    }
}