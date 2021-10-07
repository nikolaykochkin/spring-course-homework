package org.example.service;

import org.example.domain.Quiz;
import org.springframework.context.MessageSource;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TestingServiceImpl implements TestingService {
    private final QuizService quizService;
    private final PrintStream out;
    private final MessageSource messageSource;
    private final Locale locale;

    private Scanner scanner;

    private String firstName;
    private String secondName;

    private int totalQuestions;
    private int correctAnswers;

    private boolean testAvailable;
    private boolean resultAvailable;

    public TestingServiceImpl(QuizService quizService, InputStream in, PrintStream out, MessageSource messageSource, Locale locale) {
        this.quizService = quizService;
        this.out = out;
        this.messageSource = messageSource;
        this.locale = locale;
        setupScanner(in);
    }

    @Override
    public void start() {
        out.println(messageSource.getMessage("testing.first_name", new String[]{}, locale));
        firstName = scanner.nextLine();
        out.println(messageSource.getMessage("testing.second_name", new String[]{}, locale));
        secondName = scanner.nextLine();
        out.println(messageSource.getMessage("testing.hello", new String[]{firstName, secondName}, locale));

        testAvailable = true;
        resultAvailable = false;
    }

    @Override
    public void test() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        for (Quiz quiz : quizzes) {
            out.println(quiz.getQuestion());
            int counter = 1;
            for (String answer : quiz.getAnswers()) {
                out.printf("%d. %s%n", counter++, answer);
            }
            counter--;
            out.println(messageSource.getMessage("testing.answer.enter",
                    new String[]{String.valueOf(counter)}, locale));
            int answer = 0;
            while (answer < 1 || answer > counter) {
                try {
                    answer = Integer.parseInt(scanner.nextLine());
                    if (answer < 1 || answer > counter) {
                        out.println(messageSource.getMessage("testing.answer.error.unbound",
                                new String[]{String.valueOf(counter)}, locale));
                    }
                } catch (NumberFormatException e) {
                    out.println(messageSource.getMessage("testing.answer.error.nan", new String[]{}, locale));
                }
            }
            totalQuestions++;
            correctAnswers += quizService.checkAnswer(quiz, answer) ? 1 : 0;
        }
        testAvailable = false;
        resultAvailable = true;
    }

    @Override
    public void printStats() {
        String[] args = {firstName, secondName, String.valueOf(correctAnswers), String.valueOf(totalQuestions)};
        out.println(messageSource.getMessage("testing.stats", args, locale));
        testAvailable = false;
        resultAvailable = false;
    }

    @Override
    public boolean testAvailable() {
        return testAvailable;
    }

    @Override
    public boolean resultAvailable() {
        return resultAvailable;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setupScanner(InputStream in) {
        this.scanner = new Scanner(in);
    }
}
