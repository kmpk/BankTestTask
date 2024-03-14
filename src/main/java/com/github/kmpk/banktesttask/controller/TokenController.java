package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.security.JwtService;
import com.github.kmpk.banktesttask.to.AuthResponseTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.github.kmpk.banktesttask.controller.TokenController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Token Controller", description = "Controller responsible for handling JWT token retrieval")
public class TokenController {
    public static final String REST_URL = "/token";
    private final JwtService jwtService;

    @PostMapping
    @SecurityRequirement(name = "basic")
    @Operation(summary = "Returns user JWT access token",
            description = "Use basic authentication to get user token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401")
    })
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseTo signIn(Authentication authentication) {
        log.info("User {} requests access token", authentication.getName());
        return new AuthResponseTo(jwtService.generateToken(authentication));
    }
}
