package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger log = LoggerFactory.getLogger(Application.class);
    private final Environment environment;

    public Application(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("");
        log.info("--------------------Properties--------------------");
        log.info("database.username={}", environment.getProperty("database.username"));
        log.info("database.password={}", environment.getProperty("database.password"));
        log.info("database.platform={}", environment.getProperty("database.platform"));
        log.info("--------------------------------------------------");
        log.info("");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}