package org.example.config;

import org.example.service.QuizService;
import org.example.service.TestingService;
import org.example.service.TestingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public TestingService testingService(QuizService quizService) {
        return new TestingServiceImpl(quizService, System.in, System.out);
    }
}
