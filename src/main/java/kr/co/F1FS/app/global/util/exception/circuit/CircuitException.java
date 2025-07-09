package kr.co.F1FS.app.global.util.exception.circuit;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class CircuitException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public CircuitException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
