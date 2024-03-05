package com.github.kmpk.banktesttask.to;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestTo(@NotBlank String login, @NotBlank String password) {
}
