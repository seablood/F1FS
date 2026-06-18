package kr.co.F1FS.app.global.util.exception.form;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class PostRoomFormException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public PostRoomFormException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
