package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.example.service.TestingService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        TestingService testingService = context.getBean(TestingService.class);
        testingService.start();
        testingService.test();
        testingService.printStats();
    }
}
