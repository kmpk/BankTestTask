package com.github.kmpk.banktesttask.service;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;

@TestConfiguration
public class TestConfig {
    //WebEnvironment is set to NONE - include converters manually
    @Bean
    public static BeanFactoryPostProcessor conversionServicePostProcessor() {
        return new RsaKeyConversionServicePostProcessor();
    }
}
