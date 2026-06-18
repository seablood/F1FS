package kr.co.F1FS.app.global.util.exception.postRoom;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class PostRoomException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public PostRoomException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
