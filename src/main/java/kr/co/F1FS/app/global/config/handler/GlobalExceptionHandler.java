/*package kr.co.F1FS.app.config.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import kr.co.F1FS.app.application.SlackService;
import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ErrorMessages;
import kr.co.F1FS.app.global.util.ExceptionType;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.email.EmailException;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.redis.RedisException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SlackService slackService;

    @ExceptionHandler({IllegalArgumentException.class,
                        HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex){
        String errorMessage = ex.getMessage();
        sendMessage(ex, HttpStatus.NOT_FOUND);
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
        sendMessage(ex, HttpStatus.BAD_REQUEST);
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
        sendMessage(ex, HttpStatus.BAD_REQUEST);
        log.error(errorMessages.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    @ExceptionHandler({PostException.class,
                        UserException.class,
                        ReplyException.class,
                        ConstructorException.class,
                        DriverException.class,
                        RedisException.class,
                        EmailException.class})
    public ResponseEntity<String> handlePostException(BaseException ex){
        ExceptionType exceptionType = ex.getExceptionType();
        sendExceptionType(exceptionType);
        log.error(exceptionType.getMessage());

        return ResponseEntity.status(exceptionType.getHttpStatus()).body(exceptionType.getMessage());
    }

    public void sendExceptionType(ExceptionType exceptionType){
        HashMap<String, String> data = new HashMap<>();
        data.put("에러 로그", exceptionType.getMessage());
        slackService.sendErrorMessage(exceptionType.getErrorName(), data);
    }

    public void sendMessage(Exception e, HttpStatus httpStatus){
        HashMap<String, String> data = new HashMap<>();
        data.put("에러 로그", e.getMessage());
        slackService.sendErrorMessage(httpStatus.name(), data);
    }
}*/
