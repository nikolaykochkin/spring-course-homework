package org.example.dao;

import org.example.domain.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository("quizDao")
public class QuizDaoCsv implements QuizDao {
    private static final int QUESTION_POSITION = 0;
    private final URI uri;
    private List<Quiz> quizzes;

    public QuizDaoCsv(@Value("${application.quiz.path}") String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("Quizzes file path not found");
        }
        try {
            this.uri = resource.toURI();
            if ("jar".equals(uri.getScheme())) {
                try {
                    FileSystems.getFileSystem(uri);
                } catch (FileSystemNotFoundException e) {
                    // in this case we need to initialize it first:
                    FileSystems.newFileSystem(uri, Collections.emptyMap());
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new IllegalArgumentException("Something went wrong while getting resource URI", e);
        }
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        if (quizzes == null) {
            init();
        }
        return quizzes;
    }

    private void init() {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(uri));
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read quizzes csv file: " + uri.getPath(), e);
        }

        quizzes = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split("\\s*,\\s*");
            String question = split[QUESTION_POSITION];
            int answer = Integer.parseInt(split[split.length - 1]);
            List<String> answers = new ArrayList<>(Arrays.asList(split).subList(1, split.length - 1));
            quizzes.add(new Quiz(question, answers, Collections.singleton(answer)));
        }
    }
}
