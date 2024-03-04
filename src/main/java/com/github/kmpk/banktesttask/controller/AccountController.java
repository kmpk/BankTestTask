package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.security.AuthUser;
import com.github.kmpk.banktesttask.service.AccountService;
import com.github.kmpk.banktesttask.to.TransferRequestTo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.github.kmpk.banktesttask.controller.AccountController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    static final String REST_URL = "/api/account";
    private final AccountService service;

    @SecurityRequirement(name = "jwt")
    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@RequestBody @Valid TransferRequestTo request, @AuthenticationPrincipal AuthUser authUser) {
        log.info("transfer {} from {} to {}", request.amount(), authUser.id(), request.recipientId());
        service.transfer(authUser.id(), request.recipientId(), request.amount(), request.operationDateTime());
    }
}
