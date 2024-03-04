package com.github.kmpk.banktesttask.to;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserTo(Integer id, String login, String phone, String email, LocalDate birthDate, String fullName,
                     BigDecimal balance) {
}
