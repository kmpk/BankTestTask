package com.github.kmpk.banktesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankTestTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankTestTaskApplication.class, args);
    }
}
