package kr.co.F1FS.app.domain.file.application.port.in;

import kr.co.F1FS.app.domain.file.domain.UploadFile;

public interface QueryUploadFileUseCase {
    UploadFile findByUrl(String url);
}
