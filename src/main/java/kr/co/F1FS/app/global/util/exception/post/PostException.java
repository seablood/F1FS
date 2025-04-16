package kr.co.F1FS.app.global.util.exception.post;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class PostException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public PostException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType(){
        return this.exceptionType;
    }
}
