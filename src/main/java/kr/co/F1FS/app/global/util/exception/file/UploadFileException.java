package kr.co.F1FS.app.global.util.exception.file;

import kr.co.F1FS.app.global.util.BaseException;
import kr.co.F1FS.app.global.util.ExceptionType;

public class UploadFileException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public UploadFileException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
