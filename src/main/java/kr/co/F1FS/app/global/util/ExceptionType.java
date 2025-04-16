package kr.co.F1FS.app.global.util;

import org.springframework.http.HttpStatus;

public interface ExceptionType {
    String getErrorName();
    HttpStatus getHttpStatus();
    String getMessage();
}
