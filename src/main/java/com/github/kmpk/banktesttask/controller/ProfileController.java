package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.security.AuthUser;
import com.github.kmpk.banktesttask.service.UserService;
import com.github.kmpk.banktesttask.to.UpdateEmailRequestTo;
import com.github.kmpk.banktesttask.to.UpdatePhoneRequestTo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.github.kmpk.banktesttask.controller.ProfileController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    static final String REST_URL = "/api/profile";
    private final UserService service;

    @SecurityRequirement(name = "jwt")
    @PatchMapping(path = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UpdateEmailRequestTo request, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update email to {} for user id={}", request.email(), authUser.id());
        service.editEmail(authUser.id(), request.email());
    }

    @SecurityRequirement(name = "jwt")
    @PatchMapping(path = "/phone", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UpdatePhoneRequestTo request, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update phone to {} for user id={}", request.phone(), authUser.id());
        service.editPhone(authUser.id(), request.phone());
    }
}
