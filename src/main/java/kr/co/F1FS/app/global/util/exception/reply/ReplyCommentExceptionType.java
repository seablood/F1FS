package kr.co.F1FS.app.global.util.exception.reply;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReplyCommentExceptionType implements ExceptionType {
    REPLY_COMMENT_NOT_FOUND("REPLY_COMMENT_NOT_FOUND", HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    NOT_AUTHORITY_UPDATE_REPLY_COMMENT("NOT_AUTHORITY_UPDATE_REPLY_COMMENT", HttpStatus.FORBIDDEN, "업데이트 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_REPLY_COMMENT("NOT_AUTHORITY_DELETE_REPLY_COMMENT", HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");

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
