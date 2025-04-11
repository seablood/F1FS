package kr.co.F1FS.app.util.exception.constructor;

import kr.co.F1FS.app.util.BaseException;
import kr.co.F1FS.app.util.ExceptionType;

public class ConstructorException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public ConstructorException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
