package kr.co.F1FS.app.util.exception.redis;

import kr.co.F1FS.app.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum RedisExceptionType implements ExceptionType {
    REDIS_TEMPLATE_ERROR("REDIS_TEMPLATE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Redis 템플릿 오류");

    private final String errorName;
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getErrorName() {
        return this.errorName;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
