package com.github.kmpk.banktesttask.to;

import jakarta.validation.constraints.Pattern;

public record UpdatePhoneRequestTo(@Pattern(regexp = "([0-9]{5,16})") String phone) {
}
