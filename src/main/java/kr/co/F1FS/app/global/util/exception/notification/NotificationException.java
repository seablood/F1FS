package kr.co.F1FS.app.global.util.exception.notification;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class NotificationException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public NotificationException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
