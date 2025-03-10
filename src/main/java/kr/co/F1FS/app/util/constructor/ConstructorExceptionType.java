package kr.co.F1FS.app.util.constructor;

import kr.co.F1FS.app.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ConstructorExceptionType implements ExceptionType {
    CONSTRUCTOR_NOT_FOUND("CONSTRUCTOR_NOT_FOUND", HttpStatus.NOT_FOUND, "컨스트럭터를 찾을 수 없습니다."),
    CONSTRUCTOR_RECORD_ERROR("CONSTRUCTOR_RECORD_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "현 레코드를 찾을 수 없습니다.");

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
