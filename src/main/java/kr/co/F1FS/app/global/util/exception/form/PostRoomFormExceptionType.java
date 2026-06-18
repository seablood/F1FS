package kr.co.F1FS.app.global.util.exception.form;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PostRoomFormExceptionType implements ExceptionType {
    POST_ROOM_FORM_NOT_FOUND("POST_ROOM_FORM_NOT_FOUND", HttpStatus.NOT_FOUND, "게시판 신청글을 찾을 수 없습니다."),
    NOT_AUTHORITY_UPDATE_POST_ROOM_FORM("NOT_AUTHORITY_UPDATE_POST_ROOM_FORM", HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_POST_ROOM_FORM("NOT_AUTHORITY_DELETE_POST_ROOM_FORM", HttpStatus.FORBIDDEN, "삭제 권한이 없습니다."),
    ALREADY_HAS_FORM("ALREADY_HAS_FORM", HttpStatus.BAD_REQUEST, "이미 게시판 신청을 한 유저입니다.");

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
