package kr.co.F1FS.app.domain.file.application.mapper;

import kr.co.F1FS.app.domain.file.domain.UploadFile;
import kr.co.F1FS.app.domain.file.presentation.dto.ResponseUploadFileDTO;
import org.springframework.stereotype.Component;

@Component
public class UploadFileMapper {
    public UploadFile toUploadFile(String originalName, String storedName, String url, Long size){
        return UploadFile.builder()
                .originalName(originalName)
                .storedName(storedName)
                .url(url)
                .size(size)
                .build();
    }

    public ResponseUploadFileDTO toResponseUploadFileDTO(String url){
        return ResponseUploadFileDTO.builder()
                .url(url)
                .build();
    }
}
