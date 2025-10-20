package kr.co.F1FS.app.global.util.exception.chat;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ChatRoomExceptionType implements ExceptionType {
    CHAT_ROOM_NOT_FOUND("CHAT_ROOM_NOT_FOUND", HttpStatus.NOT_FOUND, "채팅을 찾을 수 없습니다."),
    NOT_AUTHORITY_UPDATE_CHAT_ROOM("NOT_AUTHORITY_UPDATE_CHAT_ROOM", HttpStatus.FORBIDDEN, "업데이트 권한이 없습니다."),
    CONDITION_ERROR_CHAT_ROOM("CONDITION_ERROR_CHAT_ROOM", HttpStatus.BAD_REQUEST, "정렬 방식이 잘못되었습니다.");

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
