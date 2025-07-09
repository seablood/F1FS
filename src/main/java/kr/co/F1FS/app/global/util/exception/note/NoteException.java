package kr.co.F1FS.app.global.util.exception.note;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class NoteException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public NoteException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
