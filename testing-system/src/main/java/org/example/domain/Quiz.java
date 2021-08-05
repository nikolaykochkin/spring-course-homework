package org.example.domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Quiz {
    private final String question;
    private final List<String> answers;
    private final Set<Integer> rightAnswers;

    public Quiz(String question, List<String> answers, Set<Integer> rightAnswers) {
        this.question = question;
        this.answers = answers;
        this.rightAnswers = rightAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public Set<Integer> getRightAnswers() {
        return rightAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return question.equals(quiz.question) && answers.equals(quiz.answers) && rightAnswers.equals(quiz.rightAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answers, rightAnswers);
    }
}
