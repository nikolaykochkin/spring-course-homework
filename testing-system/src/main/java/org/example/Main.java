package org.example;

import org.example.service.TestingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        TestingService testingService = context.getBean(TestingService.class);
        testingService.start();
        testingService.test();
        testingService.printStats();
        context.stop();
    }
}
