package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.service.UserService;
import com.github.kmpk.banktesttask.to.CreateUserRequestTo;
import com.github.kmpk.banktesttask.to.UserTo;
import com.github.kmpk.banktesttask.validation.PhoneOrEmailExistValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "User Management Controller", description = "Controller responsible for managing users")
public class UserManagementController {
    static final String REST_URL = "/api/admin/users";

    private final UserService service;
    private final PhoneOrEmailExistValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @PostMapping
    @SecurityRequirements
    @Operation(summary = "Creates new user",
            description = "Provide data to create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "422")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public UserTo create(@RequestBody @Valid CreateUserRequestTo request) {
        log.info("Creating user {}", request);
        return service.createUser(request);
    }
}
