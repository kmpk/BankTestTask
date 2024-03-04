package com.github.kmpk.banktesttask.to;

import jakarta.validation.constraints.Email;

public record UpdateEmailRequestTo(@Email String email) {
}
