package com.example.clevervision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling // Enable scheduling

public class CleverVisionApplication {

    public static void main(String[] args) {

        SpringApplication.run(CleverVisionApplication.class, args);
    }

}
