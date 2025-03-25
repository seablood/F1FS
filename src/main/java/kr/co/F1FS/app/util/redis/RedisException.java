package kr.co.F1FS.app.util.redis;

import kr.co.F1FS.app.util.BaseException;
import kr.co.F1FS.app.util.ExceptionType;

public class RedisException extends RuntimeException implements BaseException {
    private ExceptionType exceptionType;

    public RedisException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
