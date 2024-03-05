package com.github.kmpk.banktesttask;

import com.github.kmpk.banktesttask.properties.InterestProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(InterestProperties.class)
public class BankTestTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankTestTaskApplication.class, args);
    }
}
