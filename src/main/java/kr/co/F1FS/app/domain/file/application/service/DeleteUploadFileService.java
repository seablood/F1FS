package kr.co.F1FS.app.domain.file.application.service;

import kr.co.F1FS.app.domain.file.application.port.in.DeleteUploadFileUseCase;
import kr.co.F1FS.app.domain.file.application.port.in.QueryUploadFileUseCase;
import kr.co.F1FS.app.domain.file.application.port.out.UploadFileJpaPort;
import kr.co.F1FS.app.domain.file.domain.UploadFile;
import kr.co.F1FS.app.global.util.exception.file.UploadFileException;
import kr.co.F1FS.app.global.util.exception.file.UploadFileExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeleteUploadFileService implements DeleteUploadFileUseCase {
    private final QueryUploadFileUseCase queryUploadFileUseCase;
    private final UploadFileJpaPort fileJpaPort;

    @Override
    @Async
    @Transactional
    public void deleteFile(Set<String> urls) {
        for (String url : urls){
            UploadFile file = queryUploadFileUseCase.findByUrl(url);
            String fileName = extractFileName(url);
            Path path = Paths.get("uploads/" + fileName);

            try {
                Files.deleteIfExists(path);
                fileJpaPort.delete(file);
            } catch (IOException e){
                throw new UploadFileException(UploadFileExceptionType.UPLOAD_FILE_ERROR);
            }
        }
    }

    public String extractFileName(String url) {
        int queryIndex = url.indexOf("?");
        if (queryIndex != -1) {
            url = url.substring(0, queryIndex);
        }

        return url.substring(url.lastIndexOf("/") + 1);
    }
}
