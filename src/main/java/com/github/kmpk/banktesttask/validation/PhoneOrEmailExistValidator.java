package com.github.kmpk.banktesttask.validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PhoneOrEmailExistValidator implements org.springframework.validation.Validator {
    public static final String MESSAGE = "Must have email or phone";

    @Override
    public boolean supports(Class<?> clazz) {
        return HasPhoneAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasPhoneAndEmail user = ((HasPhoneAndEmail) target);
        if (user.getEmail() == null && user.getPhone() == null) {
            errors.rejectValue("email,phone", "", MESSAGE);
        }
    }
}
