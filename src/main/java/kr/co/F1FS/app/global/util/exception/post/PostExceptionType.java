package kr.co.F1FS.app.global.util.exception.post;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PostExceptionType implements ExceptionType {
    POST_NOT_FOUND("POST_NOT_FOUND", HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    NOT_AUTHORITY_UPDATE_POST("NOT_AUTHORITY_UPDATE_POST", HttpStatus.FORBIDDEN, "업데이트 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_POST("NOT_AUTHORITY_DELETE_POST", HttpStatus.FORBIDDEN, "삭제 권한이 없습니다."),
    CONDITION_ERROR_POST("CONDITION_ERROR_POST", HttpStatus.INTERNAL_SERVER_ERROR, "정렬 방식이 잘못되었습니다.");

    private final String errorName;
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getErrorName(){
        return this.errorName;
    }

    @Override
    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
