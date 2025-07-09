package kr.co.F1FS.app.global.util.exception.circuit;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CircuitExceptionType implements ExceptionType {
    CIRCUIT_NOT_FOUND("CIRCUIT_NOT_FOUND", HttpStatus.NOT_FOUND, "서킷 정보가 존재하지 않습니다.");

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
