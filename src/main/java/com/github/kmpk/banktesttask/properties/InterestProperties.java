package com.github.kmpk.banktesttask.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "bank.interest")
@Validated
@Setter
@Getter
public class InterestProperties {
    @Positive
    @NotNull
    private Integer interval;

    @Min(1)
    @NotNull
    private BigDecimal rate;

    @Min(1)
    @NotNull
    private BigDecimal ceiling;
}
