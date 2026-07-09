package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ============================================================================
 * The Global Exception Handling Layer
 * ============================================================================
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody combined.
 *
 * @ControllerAdvice makes this class apply GLOBALLY across every
 * @RestController in the application - we don't need to repeat
 * try/catch blocks or @ExceptionHandler methods in every single controller.
 *
 * Each @ExceptionHandler method here "listens" for a specific exception
 * type; whenever ANY controller method throws that exception (directly,
 * or indirectly from the Service layer it calls), Spring intercepts it
 * and routes it here instead of letting it become an ugly, unstructured
 * 500 error with a raw stack trace exposed to the client.
 *
 * This is exactly what makes error handling "layered" - the Service layer
 * just throws meaningful, specific exceptions (ResourceNotFoundException,
 * DuplicateResourceException) without knowing or caring about HTTP status
 * codes at all. Converting "a business-level problem" into "the correct
 * HTTP status code + a clean JSON error body" is entirely this layer's job.
 * ============================================================================
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // --- Handles our custom "not found" exception -> HTTP 404 ---
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // --- Handles our custom "duplicate" exception -> HTTP 409 Conflict ---
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException ex, HttpServletRequest request) {

        ErrorResponse error = buildError(HttpStatus.CONFLICT, ex.getMessage(), request, null);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // --- Handles @Valid validation failures on request DTOs -> HTTP 400 ---
    // Triggered automatically whenever a @RequestBody @Valid parameter fails
    // one or more Bean Validation constraints (e.g., @NotBlank, @Min, @Pattern).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorResponse error = buildError(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields",
                request,
                validationErrors
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // --- Catch-all fallback for anything unexpected -> HTTP 500 ---
    // IMPORTANT: never leak raw exception messages/stack traces to the client
    // in a real production system - log the real exception server-side, and
    // return a generic, safe message to the caller instead.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        // In a real project: log.error("Unexpected error", ex);
        ErrorResponse error = buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request,
                null
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse buildError(HttpStatus status, String message,
                                     HttpServletRequest request, List<String> validationErrors) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .validationErrors(validationErrors)
                .build();
    }
}
