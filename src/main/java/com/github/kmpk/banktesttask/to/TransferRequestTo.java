package com.github.kmpk.banktesttask.to;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRequestTo(int recipientId, @NotNull LocalDateTime operationDateTime,
                                @NotNull @Positive @Digits(integer = 13, fraction = 2) BigDecimal amount) {
}
