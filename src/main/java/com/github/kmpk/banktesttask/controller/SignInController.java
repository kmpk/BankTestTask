package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.security.AuthenticationService;
import com.github.kmpk.banktesttask.to.AuthResponseTo;
import com.github.kmpk.banktesttask.to.AuthRequestTo;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signIn")
@RequiredArgsConstructor
@Slf4j
public class SignInController {
    private final AuthenticationService authenticationService;

    @PostMapping
    @SecurityRequirements
    public AuthResponseTo signIn(@RequestBody @Valid AuthRequestTo request) {
        log.info("User {} requests access token", request.login());
        return authenticationService.signIn(request);
    }
}
