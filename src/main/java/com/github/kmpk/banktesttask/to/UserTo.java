package com.github.kmpk.banktesttask.to;

import java.time.LocalDate;

public record UserTo(Integer id, String login, String phone, String email, LocalDate birthDate, String fullName) { //TODO: include balance?
}
