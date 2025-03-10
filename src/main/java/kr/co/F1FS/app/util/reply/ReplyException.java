package kr.co.F1FS.app.util.reply;

import kr.co.F1FS.app.util.BaseException;
import kr.co.F1FS.app.util.ExceptionType;

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
