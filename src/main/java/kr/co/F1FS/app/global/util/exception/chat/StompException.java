package kr.co.F1FS.app.global.util.exception.chat;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class StompException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public StompException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
