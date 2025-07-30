package kr.co.F1FS.app.global.util.exception.grandprix;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum GrandPrixExceptionType implements ExceptionType {
    GRAND_PRIX_NOT_FOUND("GRAND_PRIX_NOT_FOUND", HttpStatus.NOT_FOUND, "그랑프리가 존재하지 않습니다."),
    GRAND_PRIX_CONDITION_ERROR("GRAND_PRIX_CONDITION_ERROR", HttpStatus.BAD_REQUEST, "정렬 방식이 잘못되었습니다.");

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
