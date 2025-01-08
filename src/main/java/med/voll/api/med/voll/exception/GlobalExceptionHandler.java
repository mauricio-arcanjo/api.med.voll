package med.voll.api.med.voll.exception;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice //Becomes main handler for exceptions in spring
public class GlobalExceptionHandler {

    //Handle specific exception - AccountException
    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<ErrorDetails> handleAccountException(AppointmentException exception,
                                                               WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "APPOINTMENT_NOT_CREATED"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //Handle specific exception - Entity not found
    //Alura method. Returns error 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(){

        return ResponseEntity.notFound().build();

    }
    @ExceptionHandler(MappingException.class)
    public ResponseEntity handleMappingException(){

        return ResponseEntity.notFound().build();

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<FieldError> errors = methodArgumentNotValidException.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorDetails::new).toList());
    }

    //Handle Generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception exception,
                                                               WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}