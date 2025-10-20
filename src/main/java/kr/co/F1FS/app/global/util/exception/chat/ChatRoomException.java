package kr.co.F1FS.app.global.util.exception.chat;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class ChatRoomException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public ChatRoomException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
