package org.example.service;

import org.example.domain.Quiz;

import java.util.List;

public interface QuizService {
    List<Quiz> getAllQuizzes();

    boolean checkAnswer(Quiz quiz, int answer);
}
