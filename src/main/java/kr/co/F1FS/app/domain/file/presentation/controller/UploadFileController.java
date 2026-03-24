package kr.co.F1FS.app.domain.file.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.file.application.port.in.CreateUploadFileUseCase;
import kr.co.F1FS.app.domain.file.presentation.dto.ResponseUploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@Tag(name = "파일 시스템", description = "파일 업로드 및 다운로드 관련 서비스")
public class UploadFileController {
    private final CreateUploadFileUseCase createUploadFileUseCase;

    @PostMapping("/upload")
    @Operation(summary = "파일 업로드", description = "파일 업로드 API")
    public ResponseEntity<ResponseUploadFileDTO> uploadFile(@RequestPart MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(createUploadFileUseCase.upload(file));
    }
}
