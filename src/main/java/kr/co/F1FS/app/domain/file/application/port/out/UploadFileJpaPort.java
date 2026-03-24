package kr.co.F1FS.app.domain.file.application.port.out;

import kr.co.F1FS.app.domain.file.domain.UploadFile;

public interface UploadFileJpaPort {
    UploadFile save(UploadFile file);
    UploadFile findByUrl(String url);
    void delete(UploadFile file);
}
