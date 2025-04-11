package kr.co.F1FS.app.util.exception.user;

import kr.co.F1FS.app.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserExceptionType implements ExceptionType {
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_AUTHENTICATION_ERROR("USER_AUTHENTICATION_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "인증에 실패하였습니다."),
    TOKEN_VALIDATE_ERROR("TOKEN_VALIDATE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "유효한 토큰이 아닙니다.");

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
