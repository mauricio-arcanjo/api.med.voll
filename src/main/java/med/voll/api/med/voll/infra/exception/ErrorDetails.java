package med.voll.api.med.voll.infra.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp,
                           String message,
                           String details,
                           String errorCode) {
}