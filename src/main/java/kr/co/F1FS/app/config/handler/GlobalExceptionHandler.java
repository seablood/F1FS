package kr.co.F1FS.app.config.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import kr.co.F1FS.app.util.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class,
                        HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex){
        String errorMessage = ex.getMessage();
        log.error(errorMessage);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessages> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        final List<String> errorMessageList = fieldErrors.stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ErrorMessages errorMessages = new ErrorMessages(errorMessageList);
        log.error(errorMessages.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorMessages> handleConstraintViolationException(ConstraintViolationException ex){
        final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        final List<String> errorMessageList = violations.stream()
                .map(violation -> violation.getMessage())
                .toList();

        ErrorMessages errorMessages = new ErrorMessages(errorMessageList);
        log.error(errorMessages.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }
}
