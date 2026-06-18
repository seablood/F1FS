package kr.co.F1FS.app.global.util.exception.postRoomSuspension;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PostRoomSuspensionExceptionType implements ExceptionType {
    POST_ROOM_SUSPENSION_NOT_FOUND("POST_ROOM_SUSPENSION_NOT_FOUND", HttpStatus.NOT_FOUND, "내역을 찾을 수 없습니다."),
    NOT_AUTHORITY_SUSPENSION_SAVE("NOT_AUTHORITY_SUSPENSION_SAVE", HttpStatus.FORBIDDEN, "게시판 이용 차단 권한이 없습니다."),
    NOT_AUTHORITY_SUSPENSION_UPDATE("NOT_AUTHORITY_SUSPENSION_UPDATE", HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    NOT_AUTHORITY_SUSPENSION_DELETE("NOT_AUTHORITY_SUSPENSION_DELETE", HttpStatus.FORBIDDEN, "해제 권한이 없습니다."),
    CONDITION_ERROR_POST_ROOM_SUSPENSION("CONDITION_ERROR_POST_ROOM_SUSPENSION", HttpStatus.BAD_REQUEST, "정렬 방식이 잘못되었습니다.");

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
