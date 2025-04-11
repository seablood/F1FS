package kr.co.F1FS.app.util.exception.driver;

import kr.co.F1FS.app.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum DriverExceptionType implements ExceptionType {
    DRIVER_NOT_FOUND("DRIVER_NOT_FOUND", HttpStatus.NOT_FOUND, "드라이버를 찾을 수 없습니다."),
    DRIVER_RECORD_ERROR("DRIVER_RECORD_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "현 레코드를 찾을 수 없습니다."),
    DRIVER_DEBUT_ERROR("DRIVER_DEBUT_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "데뷔 레코드를 찾을 수 없습니다."),
    DRIVER_TRANSFER_ERROR("DRIVER_TRANSFER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "드라이버 소속 변경 에러");

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
