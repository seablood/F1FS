package kr.co.F1FS.app.global.util.exception.note;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum NoteExceptionType implements ExceptionType {
    NOTE_NOT_FOUND("NOTE_NOT_FOUND", HttpStatus.NOT_FOUND, "쪽지를 불러오는데 실패하였습니다."),
    NOTE_IS_READ("NOTE_IS_READ", HttpStatus.BAD_REQUEST, "읽은 쪽지는 전송을 취소할 수 없습니다.");

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
