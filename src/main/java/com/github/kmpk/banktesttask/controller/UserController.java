package com.github.kmpk.banktesttask.controller;

import com.github.kmpk.banktesttask.service.UserService;
import com.github.kmpk.banktesttask.to.PageResultTo;
import com.github.kmpk.banktesttask.to.UserTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "User Controller", description = "Controller responsible for searching users")
public class UserController {
    static final String REST_URL = "/api/users";
    private final UserService service;

    @GetMapping(path = "/by-full-name")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Searches users by full name, paginates and sorts results",
            description = "Provide a full name for searching, page parameters for result pagination, and sorting parameters (optional) for sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "422")
    })
    @ResponseStatus(HttpStatus.OK)
    public PageResultTo findByFullName(@RequestParam String fullName,
                                       @Parameter(example = """
                                               {
                                                 "page": 0,
                                                 "size": 10,
                                                 "sort": [
                                                   "id", "desc", "phone", "asc"
                                                 ]
                                               }
                                               """) Pageable pageable) {
        log.info("Search users by full name {}, parameters: {}", fullName, pageable);
        return service.findAllByFullNameLike(fullName, pageable);
    }

    @GetMapping(path = "/after-birth-date")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Searches users born after specified date, paginates and sorts results",
            description = "Provide a date for searching, page parameters for result pagination, and sorting parameters (optional) for sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "422")
    })
    @ResponseStatus(HttpStatus.OK)
    public PageResultTo findByBirthDate(@RequestParam LocalDate birthDate,
                                        @Parameter(example = """
                                                {
                                                  "page": 0,
                                                  "size": 10,
                                                  "sort": [
                                                    "id", "desc", "phone", "asc"
                                                  ]
                                                }
                                                """) Pageable pageable) {
        log.info("Search users by birthdate {}, parameters: {}", birthDate, pageable);
        return service.findAllAfterBirthDate(birthDate, pageable);
    }

    @GetMapping(path = "/by-phone")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Searches for a user with specified phone",
            description = "Provide a phone for searching")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "422")
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserTo> findByPhone(@RequestParam @Pattern(regexp = "([0-9]{5,16})") String phone) {
        log.info("Search user by phone {}", phone);
        return service.findByPhone(phone)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/by-email")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Searches for a user with specified email (case-insensitive)",
            description = "Provide an email for searching")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "422")
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserTo> findByEmail(@RequestParam @Email String email) {
        log.info("Search user by email {}", email);
        return service.findByEmailIgnoreCase(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
