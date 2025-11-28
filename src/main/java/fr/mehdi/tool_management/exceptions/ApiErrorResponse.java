package fr.mehdi.tool_management.exceptions;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    private String                  error;
    private String                  message;
    private Map<String, String>     details;

    public ApiErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ApiErrorResponse(String error, Map<String, String> details) {
        this.error = error;
        this.details = details;
    }
    
}
