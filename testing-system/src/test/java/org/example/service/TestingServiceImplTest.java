package org.example.service;

import org.example.domain.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestingServiceImplTest {
    private TestingServiceImpl testingService;
    private QuizService quizService;
    private OutputStream out;

    @BeforeEach
    void setUp() {
        quizService = mock(QuizService.class);
        out = new ByteArrayOutputStream();
        testingService = new TestingServiceImpl(quizService, InputStream.nullInputStream(), new PrintStream(out));
    }

    @Test
    void should_PrintGreeting_When_EnterName() {
        String expected = String.format("Please enter your first name:%n" +
                "Please enter your second name:%n" +
                "Hello Ivan Ivanov!%n");
        String input = String.format("Ivan%nIvanov%n");
        InputStream in = new ByteArrayInputStream(input.getBytes());
        testingService.setupScanner(in);

        testingService.start();
        String actual = out.toString();

        assertEquals(expected, actual);
    }

    @Test
    void should_PrintQuizzes_When_Test() {
        String expected = String.format("question1%n1. answer1%n2. answer2%n3. answer3%n4. answer4%n" +
                "Please enter your answer [1-4]%n");
        when(quizService.getAllQuizzes())
                .thenReturn(Collections.singletonList(new Quiz(
                        "question1",
                        List.of("answer1", "answer2", "answer3", "answer4"),
                        Collections.singleton(1))));

        String input = String.format("1%n");
        InputStream in = new ByteArrayInputStream(input.getBytes());
        testingService.setupScanner(in);

        testingService.test();
        String actual = out.toString();

        assertEquals(expected, actual);
    }

    @Test
    void should_RejectAnswer_When_AnswerNotNumber() {
        String expected = String.format("question1%n1. answer1%n2. answer2%n3. answer3%n4. answer4%n" +
                "Please enter your answer [1-4]%n" +
                "Your answer is not a number, please enter your answer again%n");
        when(quizService.getAllQuizzes())
                .thenReturn(Collections.singletonList(new Quiz(
                        "question1",
                        List.of("answer1", "answer2", "answer3", "answer4"),
                        Collections.singleton(1))));

        String input = String.format("abc%n1%n");
        InputStream in = new ByteArrayInputStream(input.getBytes());
        testingService.setupScanner(in);

        testingService.test();
        String actual = out.toString();

        assertEquals(expected, actual);
    }

    @Test
    void should_RejectAnswer_When_AnswerOutOfBound() {
        String expected = String.format("question1%n1. answer1%n2. answer2%n3. answer3%n4. answer4%n" +
                "Please enter your answer [1-4]%n" +
                "Your answer is less than 1 or greater than 4, please enter your answer again%n" +
                "Your answer is less than 1 or greater than 4, please enter your answer again%n");
        when(quizService.getAllQuizzes())
                .thenReturn(Collections.singletonList(new Quiz(
                        "question1",
                        List.of("answer1", "answer2", "answer3", "answer4"),
                        Collections.singleton(1))));

        String input = String.format("0%n5%n1%n");
        InputStream in = new ByteArrayInputStream(input.getBytes());
        testingService.setupScanner(in);

        testingService.test();
        String actual = out.toString();

        assertEquals(expected, actual);
    }

    @Test
    void should_PrintStats_When_AllFieldsAreFilled() {
        testingService.setFirstName("Ivan");
        testingService.setSecondName("Ivanov");
        testingService.setCorrectAnswers(1);
        testingService.setTotalQuestions(2);

        String expected = String.format("Ivan Ivanov your result is 1 correct answers from 2%n");

        testingService.printStats();

        String actual = out.toString();

        assertEquals(expected, actual);
    }
}