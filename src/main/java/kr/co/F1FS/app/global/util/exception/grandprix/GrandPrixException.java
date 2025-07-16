package kr.co.F1FS.app.global.util.exception.grandprix;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class GrandPrixException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public GrandPrixException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
