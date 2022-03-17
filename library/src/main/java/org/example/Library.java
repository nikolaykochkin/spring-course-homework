package org.example;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class Library {
    public static void main(String[] args) {
        SpringApplication.run(Library.class, args);
    }
}
