package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.security.AuthenticationService;
import com.github.kmpk.banktesttask.to.AuthRequestTo;
import com.github.kmpk.banktesttask.to.AuthResponseTo;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.github.kmpk.banktesttask.controller.SignInController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "SignIn Controller", description = "Controller responsible for handling JWT token retrieval")
public class SignInController {
    static final String REST_URL = "/signIn";
    private final AuthenticationService authenticationService;

    @PostMapping
    @SecurityRequirements
    @Operation(summary = "Returns user JWT access token",
            description = "Provide user credentials to get access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseTo signIn(@RequestBody @Valid AuthRequestTo request) {
        log.info("User {} requests access token", request.login());
        return authenticationService.signIn(request);
    }
}
