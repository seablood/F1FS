package kr.co.F1FS.app.util.post;

import kr.co.F1FS.app.util.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostExceptionType implements ExceptionType {
    POST_NOT_FOUND("POST_NOT_FOUND", HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    NOT_AUTHORITY_UPDATE_POST("NOT_AUTHORITY_UPDATE_POST", HttpStatus.FORBIDDEN, "업데이트 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_POST("NOT_AUTHORITY_DELETE_POST", HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");

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
