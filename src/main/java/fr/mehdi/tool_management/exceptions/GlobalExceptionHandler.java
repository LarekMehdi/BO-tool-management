package fr.mehdi.tool_management.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /** VALIDATION **/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        ApiErrorResponse response = new ApiErrorResponse("Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }

    /** CUSTOM EXCEPTIONS **/

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusExceptions(ResponseStatusException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getReason() != null ? ex.getReason() : "Error", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    /** CATCH ALL **/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        ApiErrorResponse response = new ApiErrorResponse("Internal server error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
}
