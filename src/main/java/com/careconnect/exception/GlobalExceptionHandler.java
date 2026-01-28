package com.careconnect.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.careconnect.dto.ErrorResponse;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // 400 - Validation Errors
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        log.warn("Validation failed: {}", errors);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message(errors.toString())
                .path(request.getRequestURI())
                .errorCode("VALIDATION_ERROR")
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // =========================
    // 400 - Bad Request
    // =========================
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(
            Exception ex,
            HttpServletRequest request) {

        log.warn("Bad request: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage(),
                request,
                "BAD_REQUEST"
        );
    }

    // =========================
    // 401 - Unauthorized
    // =========================
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {

        log.warn("Unauthorized access");

        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                "Authentication failed",
                request,
                "AUTH_ERROR"
        );
    }

    // =========================
    // 403 - Forbidden
    // =========================
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request) {

        log.warn("Access denied");

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "Forbidden",
                "Access is denied",
                request,
                "ACCESS_DENIED"
        );
    }

    // =========================
    // 404 - Resource Not Found
    // =========================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Resource not found: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage(),
                request,
                "RESOURCE_NOT_FOUND"
        );
    }

    // =========================
    // 409 - Conflict
    // =========================
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        log.warn("Conflict: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage(),
                request,
                "CONFLICT"
        );
    }

    // =========================
    // 500 - Internal Server Error (Fallback)
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unhandled exception", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Something went wrong. Please try again later.",
                request,
                "INTERNAL_ERROR"
        );
    }

    // =========================
    // Helper Methods
    // =========================
    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request,
            String errorCode) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .errorCode(errorCode)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    private String now() {
        return LocalDateTime.now().toString();
    }
}
