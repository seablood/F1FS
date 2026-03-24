package kr.co.F1FS.app.domain.file.application.service;

import kr.co.F1FS.app.domain.file.application.port.in.QueryUploadFileUseCase;
import kr.co.F1FS.app.domain.file.application.port.out.UploadFileJpaPort;
import kr.co.F1FS.app.domain.file.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryUploadFileService implements QueryUploadFileUseCase {
    private final UploadFileJpaPort fileJpaPort;

    @Override
    public UploadFile findByUrl(String url) {
        return fileJpaPort.findByUrl(url);
    }
}
