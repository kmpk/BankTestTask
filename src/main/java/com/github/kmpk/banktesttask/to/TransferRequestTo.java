package com.github.kmpk.banktesttask.to;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestTo(int recipientId, @Positive @Digits(integer = 13, fraction = 2) BigDecimal amount) {
}
