package kr.co.F1FS.app.global.util.exception.reply;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class ReplyException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public ReplyException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
