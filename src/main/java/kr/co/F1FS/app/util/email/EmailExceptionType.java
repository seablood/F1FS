package kr.co.F1FS.app.util.email;

import kr.co.F1FS.app.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum EmailExceptionType implements ExceptionType {
    SEND_EMAIL_ERROR("SEND_EMAIL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패하였습니다.");

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
