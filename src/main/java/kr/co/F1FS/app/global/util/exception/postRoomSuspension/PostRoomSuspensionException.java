package kr.co.F1FS.app.global.util.exception.postRoomSuspension;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class PostRoomSuspensionException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public PostRoomSuspensionException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
