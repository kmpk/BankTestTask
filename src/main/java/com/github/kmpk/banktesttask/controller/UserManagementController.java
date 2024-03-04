package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.service.UserService;
import com.github.kmpk.banktesttask.to.CreateUserRequestTo;
import com.github.kmpk.banktesttask.to.UserTo;
import com.github.kmpk.banktesttask.validation.PhoneOrEmailExistValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.github.kmpk.banktesttask.controller.UserManagementController.REST_URL;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserManagementController {
    static final String REST_URL = "/api/admin/users";

    private final UserService service;
    private final PhoneOrEmailExistValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirements
    public UserTo create(@RequestBody @Valid CreateUserRequestTo request) {
        return service.createUser(request);
    }
}
