package kr.co.F1FS.app.global.util.exception.bookmark;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class BookmarkException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public BookmarkException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
