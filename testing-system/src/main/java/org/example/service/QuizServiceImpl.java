package org.example.service;

import org.example.dao.QuizDao;
import org.example.domain.Quiz;

import java.util.Collections;
import java.util.List;

public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuizzes();
    }

    @Override
    public boolean checkAnswer(Quiz quiz, int answer) {
        return Collections.singleton(answer).equals(quiz.getRightAnswers());
    }
}
