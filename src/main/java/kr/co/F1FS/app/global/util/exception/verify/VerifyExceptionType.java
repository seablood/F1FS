package kr.co.F1FS.app.global.util.exception.verify;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum VerifyExceptionType implements ExceptionType {
    VERIFICATION_ERROR("VERIFICATION_ERROR", HttpStatus.BAD_REQUEST, "만료된 인증 코드입니다."),
    CODE_NOT_FOUND("CODE_NOT_FOUND", HttpStatus.NOT_FOUND, "잘못된 인증 코드 또는 인증 접근입니다.");

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
