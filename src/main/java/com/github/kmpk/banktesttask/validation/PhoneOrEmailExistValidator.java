package com.github.kmpk.banktesttask.validation;


import com.github.kmpk.banktesttask.to.CreateUserRequestTo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PhoneOrEmailExistValidator implements org.springframework.validation.Validator {
    public static final String MESSAGE = "Must have email or phone";

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserRequestTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateUserRequestTo user = ((CreateUserRequestTo) target);
        if (user.email() == null && user.phone() == null) {
            errors.rejectValue("email,phone", "", MESSAGE);
        }
    }
}
