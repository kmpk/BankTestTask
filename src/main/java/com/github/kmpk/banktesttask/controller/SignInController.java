package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.security.AuthenticationService;
import com.github.kmpk.banktesttask.to.AuthResponseTo;
import com.github.kmpk.banktesttask.to.AuthRequestTo;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signIn")
@RequiredArgsConstructor
public class SignInController {
    private final AuthenticationService authenticationService;

    @PostMapping
    @SecurityRequirements
    public AuthResponseTo signIn(@RequestBody @Valid AuthRequestTo request) {
        return authenticationService.signIn(request);
    }
}
