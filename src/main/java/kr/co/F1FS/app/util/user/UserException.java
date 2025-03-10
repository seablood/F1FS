package kr.co.F1FS.app.util.user;

import kr.co.F1FS.app.util.BaseException;
import kr.co.F1FS.app.util.ExceptionType;

public class UserException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public UserException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
