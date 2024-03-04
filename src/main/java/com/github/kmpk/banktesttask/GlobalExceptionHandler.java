package com.github.kmpk.banktesttask;

import com.github.kmpk.banktesttask.exception.AppValidationException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unexpectedException(Exception ex, WebRequest request) {
        log.error("Unexpected exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.error("ConstraintViolationException: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        if (ex.getMessage().contains("balance_constraint")) {
            return createCustomDataIntegrityViolationResponse(ex, request, "address", "Can't perform operation: insufficient funds");
        }
        return createProblemDetailExceptionResponse(ex, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(AppValidationException.class)
    public ResponseEntity<?> appValidationException(AppValidationException ex, WebRequest request) {
        log.error("AppValidationException: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, UNPROCESSABLE_ENTITY, request);
    }

    private ResponseEntity<Object> createCustomDataIntegrityViolationResponse(DataIntegrityViolationException ex, WebRequest request, String field, String exceptionMessage) {
        ProblemDetail body = createProblemDetail(ex, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid request content.", null, null, request);
        Map<String, String> invalidParams = new LinkedHashMap<>();
        invalidParams.put(field, exceptionMessage);
        body.setProperty("invalid_params", invalidParams);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    private ResponseEntity<?> createProblemDetailExceptionResponse(Exception ex, HttpStatusCode statusCode, WebRequest request) {
        ProblemDetail body = createProblemDetail(ex, statusCode, ex.getMessage(), null, null, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), statusCode, request);
    }
}
