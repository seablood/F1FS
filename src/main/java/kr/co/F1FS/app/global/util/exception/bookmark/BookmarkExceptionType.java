package kr.co.F1FS.app.global.util.exception.bookmark;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BookmarkExceptionType implements ExceptionType {
    CONDITION_ERROR_BOOKMARK("CONDITION_ERROR_BOOKMARK", HttpStatus.BAD_REQUEST, "정렬 방식이 잘못되었습니다."),
    BOOKMARK_NOT_FOUND("BOOKMARK_NOT_FOUND", HttpStatus.NOT_FOUND, "북마크 기록을 찾을 수 없습니다.");

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
