package kr.co.F1FS.app.global.util.exception.file;

import kr.co.F1FS.app.global.util.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UploadFileExceptionType implements ExceptionType {
    UPLOAD_FILE_ERROR("UPLOAD_FILE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    UPLOAD_NOT_FOUND("UPLOAD_NOT_FOUND", HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다.");

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
