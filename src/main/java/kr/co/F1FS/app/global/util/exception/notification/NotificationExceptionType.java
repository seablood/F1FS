package kr.co.F1FS.app.global.util.exception.notification;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum NotificationExceptionType implements ExceptionType {
    NOTIFICATION_NOT_FOUND("NOTIFICATION_NOT_FOUND", HttpStatus.NOT_FOUND, "공지를 찾을 수 없습니다."),
    NOTIFICATION_TYPE_ERROR("NOTIFICATION_TYPE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "알림 형식 오류");

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
