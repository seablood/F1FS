package kr.co.F1FS.app.domain.file.application.service;

import kr.co.F1FS.app.domain.file.application.mapper.UploadFileMapper;
import kr.co.F1FS.app.domain.file.application.port.in.CreateUploadFileUseCase;
import kr.co.F1FS.app.domain.file.application.port.out.UploadFileJpaPort;
import kr.co.F1FS.app.domain.file.domain.UploadFile;
import kr.co.F1FS.app.domain.file.presentation.dto.ResponseUploadFileDTO;
import kr.co.F1FS.app.global.util.exception.file.UploadFileException;
import kr.co.F1FS.app.global.util.exception.file.UploadFileExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUploadFileService implements CreateUploadFileUseCase {
    private final UploadFileJpaPort fileJpaPort;
    private final UploadFileMapper fileMapper;
    private final String uploadDir = "uploads/";

    @Override
    public ResponseUploadFileDTO upload(MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            String storedName = UUID.randomUUID() + "_" + originalName;

            Path path = Paths.get(uploadDir + storedName);
            Files.copy(file.getInputStream(), path);

            String url = "/files/" + storedName;

            UploadFile uploadFile = fileMapper.toUploadFile(originalName, storedName, url, file.getSize());

            fileJpaPort.save(uploadFile);

            return fileMapper.toResponseUploadFileDTO(url);

        } catch (IOException e){
            throw new UploadFileException(UploadFileExceptionType.UPLOAD_FILE_ERROR);
        }
    }
}
