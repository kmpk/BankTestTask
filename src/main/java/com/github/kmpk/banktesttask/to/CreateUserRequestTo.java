package com.github.kmpk.banktesttask.to;

import com.github.kmpk.banktesttask.validation.HasPhoneAndEmail;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserRequestTo(@NotBlank @Length(min = 5)
                                  String login,
                                  @NotBlank @Length(min = 5)
                                  String password,
                                  @Pattern(regexp = "(^$|[0-9]{5,16})")
                                  String phone,
                                  @Email
                                  String email,
                                  @NotNull
                                  LocalDate birthDate,
                                  @NotBlank @Length(min = 5)
                                  String fullName,
                                  @NotNull @Digits(integer = 13, fraction = 2)
                                  BigDecimal initialBalance) implements HasPhoneAndEmail {
    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
