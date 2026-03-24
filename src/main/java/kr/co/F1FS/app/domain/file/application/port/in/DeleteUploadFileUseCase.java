package kr.co.F1FS.app.domain.file.application.port.in;

import java.util.Set;

public interface DeleteUploadFileUseCase {
    void deleteFile(Set<String> urls);
}
