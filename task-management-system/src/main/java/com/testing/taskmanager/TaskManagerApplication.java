package com.testing.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Main entry point for the Spring Boot Task Management application.
 * This class sets up the Spring context and starts the application.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

}
