package kr.co.F1FS.app.global.util.exception.suggest;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class SuggestException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public SuggestException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
