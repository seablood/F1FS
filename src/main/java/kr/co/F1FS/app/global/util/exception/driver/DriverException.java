package kr.co.F1FS.app.global.util.exception.driver;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class DriverException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public DriverException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
