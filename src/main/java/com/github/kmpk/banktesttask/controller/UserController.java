package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.service.UserService;
import com.github.kmpk.banktesttask.to.PageResultTo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.github.kmpk.banktesttask.controller.UserController.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Slf4j
public class UserController {
    static final String REST_URL = "/api/users";
    private final UserService service;

    @SecurityRequirement(name = "jwt")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/by-full-name")
    public PageResultTo findByFullName(@RequestParam("full-name") String fullName,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                       @RequestParam(value = "sort", defaultValue = "id,asc") String[] sort) {
        return service.findAllByFullNameLike(fullName, sort, size, page);
    }

    @SecurityRequirement(name = "jwt")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/after-birth-date")
    public PageResultTo findByBirthDate(@RequestParam("birth-date") LocalDate birthDate,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                        @RequestParam(value = "sort", defaultValue = "id,asc") String[] sort) {
        return service.findAllAfterBirthDate(birthDate, sort, size, page);
    }
}
