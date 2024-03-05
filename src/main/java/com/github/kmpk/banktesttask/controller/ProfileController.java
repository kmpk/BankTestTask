package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.security.AuthUser;
import com.github.kmpk.banktesttask.service.UserService;
import com.github.kmpk.banktesttask.to.UpdateEmailRequestTo;
import com.github.kmpk.banktesttask.to.UpdatePhoneRequestTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Profile Controller", description = "Controller responsible for editing user profile")
public class ProfileController {
    static final String REST_URL = "/api/profile";
    private final UserService service;


    @PatchMapping(path = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Changes or removes user email",
            description = "Provide new email or leave as null to remove")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "422")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UpdateEmailRequestTo request, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update email to {} for user id={}", request.email(), authUser.id());
        service.editEmail(authUser.id(), request.email());
    }

    @PatchMapping(path = "/phone", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Changes or removes user phone",
            description = "Provide new phone or leave as null to remove")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "422")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UpdatePhoneRequestTo request, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update phone to {} for user id={}", request.phone(), authUser.id());
        service.editPhone(authUser.id(), request.phone());
    }
}
