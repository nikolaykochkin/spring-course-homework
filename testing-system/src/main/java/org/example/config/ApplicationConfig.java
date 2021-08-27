package org.example.config;

import org.example.service.QuizService;
import org.example.service.TestingService;
import org.example.service.TestingServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class ApplicationConfig {
    @Bean
    public TestingService testingService(QuizService quizService, MessageSource messageSource,
                                         @Value("${application.locale}") Locale locale) {
        return new TestingServiceImpl(quizService, System.in, System.out, messageSource, locale);
    }

    @Bean
    public MessageSource messageSource() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
