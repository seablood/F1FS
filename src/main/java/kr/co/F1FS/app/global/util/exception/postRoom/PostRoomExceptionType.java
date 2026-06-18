package kr.co.F1FS.app.global.util.exception.postRoom;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PostRoomExceptionType implements ExceptionType {
    POST_ROOM_NOT_FOUND("POST_ROOM_NOT_FOUND", HttpStatus.NOT_FOUND, "게시판을 찾을 수 없습니다."),
    CONDITION_ERROR_POST_ROOM("CONDITION_ERROR_POST_ROOM", HttpStatus.BAD_REQUEST, "정렬 방식이 잘못되었습니다."),
    NOT_AUTHORITY_UPDATE_POST_ROOM("NOT_AUTHORITY_UPDATE_POST_ROOM", HttpStatus.FORBIDDEN, "게시판 업데이트 권한이 없습니다."),
    NOT_AUTHORITY_PRIVATE_POST_ROOM("NOT_AUTHORITY_PRIVATE_POST_ROOM", HttpStatus.FORBIDDEN, "비공개 게시판 비밀번호가 일치하지 않습니다.");

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
