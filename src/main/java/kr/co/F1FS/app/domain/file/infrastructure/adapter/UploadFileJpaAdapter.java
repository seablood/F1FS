package kr.co.F1FS.app.domain.file.infrastructure.adapter;

import kr.co.F1FS.app.domain.file.application.port.out.UploadFileJpaPort;
import kr.co.F1FS.app.domain.file.domain.UploadFile;
import kr.co.F1FS.app.domain.file.infrastructure.repository.UploadFileRepository;
import kr.co.F1FS.app.global.util.exception.file.UploadFileException;
import kr.co.F1FS.app.global.util.exception.file.UploadFileExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UploadFileJpaAdapter implements UploadFileJpaPort {
    private final UploadFileRepository fileRepository;

    @Override
    public UploadFile save(UploadFile file) {
        return fileRepository.save(file);
    }

    @Override
    public UploadFile findByUrl(String url) {
        return fileRepository.findByUrl(url)
                .orElseThrow(() -> new UploadFileException(UploadFileExceptionType.UPLOAD_NOT_FOUND));
    }

    @Override
    public void delete(UploadFile file) {
        fileRepository.delete(file);
    }
}
