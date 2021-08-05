package org.example.service;

import org.example.dao.QuizDao;
import org.example.domain.Quiz;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {
    @InjectMocks
    private QuizServiceImpl subject;
    @Mock
    private QuizDao quizDao;

    @Test
    void should_ReturnAllQuizzesFromDao_When_GetAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz(
                "question1",
                List.of("answer1", "answer2", "answer3", "answer4"),
                Collections.singleton(1)
        ));
        quizzes.add(new Quiz(
                "question2",
                List.of("answer1", "answer2", "answer3", "answer4"),
                Collections.singleton(2)
        ));

        Mockito.when(quizDao.getAllQuizzes())
                .thenReturn(quizzes);

        List<Quiz> actual = subject.getAllQuizzes();

        assertIterableEquals(quizzes, actual);
    }

    @Test
    void should_ReturnTrue_When_CheckCorrectAnswer() {
        Quiz quiz = new Quiz(
                "question1",
                List.of("answer1", "answer2", "answer3", "answer4"),
                Collections.singleton(1)
        );

        boolean actual = subject.checkAnswer(quiz, 1);

        assertTrue(actual);
    }

    @Test
    void should_ReturnFalse_When_CheckWrongAnswer() {
        Quiz quiz = new Quiz(
                "question1",
                List.of("answer1", "answer2", "answer3", "answer4"),
                Collections.singleton(1)
        );

        boolean actual = subject.checkAnswer(quiz, 2);

        assertFalse(actual);
    }
}