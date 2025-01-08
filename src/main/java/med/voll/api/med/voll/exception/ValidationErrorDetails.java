package med.voll.api.med.voll.exception;

import org.springframework.validation.FieldError;

public record ValidationErrorDetails(
        String field,
        String message
) {
    public ValidationErrorDetails(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
