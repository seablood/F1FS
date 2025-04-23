package kr.co.F1FS.app.global.util.exception.cdSearch;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CDSearchExceptionType implements ExceptionType {
    CONDITION_ERROR_CD("CONDITION_ERROR_CD", HttpStatus.INTERNAL_SERVER_ERROR, "정렬 방식이 잘못되었습니다.");

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
