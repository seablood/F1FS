package kr.co.F1FS.app.global.util.exception.cdSearch;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class CDSearchException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public CDSearchException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return null;
    }
}
