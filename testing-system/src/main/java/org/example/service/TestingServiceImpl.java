package org.example.service;

import org.example.domain.Quiz;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class TestingServiceImpl implements TestingService {
    private final QuizService quizService;

    private PrintStream out;
    private Scanner scanner;

    private String firstName;
    private String secondName;

    private int totalQuestions;
    private int correctAnswers;

    public TestingServiceImpl(QuizService quizService, InputStream in, PrintStream out) {
        this.quizService = quizService;
        this.out = out;
        setupScanner(in);
    }

    @Override
    public void start() {
        out.println("Please enter your first name:");
        firstName = scanner.nextLine();
        out.println("Please enter your second name:");
        secondName = scanner.nextLine();
        out.printf("Hello %s %s!%n", firstName, secondName);
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
            out.printf("Please enter your answer [1-%d]%n", counter);
            int answer = 0;
            while (answer < 1 || answer > counter) {
                try {
                    answer = Integer.parseInt(scanner.nextLine());
                    if (answer < 1 || answer > counter) {
                        out.printf("Your answer is less than 1 or greater than %d, please enter your answer again%n", counter);
                    }
                } catch (NumberFormatException e) {
                    out.println("Your answer is not a number, please enter your answer again");
                }
            }
            totalQuestions++;
            correctAnswers += quizService.checkAnswer(quiz, answer) ? 1 : 0;
        }
    }

    @Override
    public void printStats() {
        out.printf("%s %s your result is %d correct answers from %d%n", firstName, secondName, correctAnswers, totalQuestions);
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
