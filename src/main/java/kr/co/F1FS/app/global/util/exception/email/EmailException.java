package kr.co.F1FS.app.global.util.exception.email;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class EmailException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public EmailException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}
