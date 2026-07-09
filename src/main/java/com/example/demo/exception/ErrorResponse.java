package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * A single, CONSISTENT error response shape used across the entire API.
 *
 * Without this, every endpoint might return errors in a different shape,
 * forcing API consumers (frontend devs, other services) to write custom
 * error-parsing logic per-endpoint. A consistent envelope means client
 * code can handle ALL errors from this API with one shared code path.
 *
 * Example JSON this produces:
 * {
 *   "timestamp": "2026-07-08T10:15:30",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Book not found with id: 99",
 *   "path": "/api/books/99",
 *   "validationErrors": null
 * }
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;
    private String error;
    private String message;
    private String path;

    // Only populated for validation failures (Section: @Valid handling) -
    // a list of "field: message" style strings, so the client knows exactly
    // which fields failed and why.
    private List<String> validationErrors;
}
