package kr.co.F1FS.app.domain.file.application.port.in;

import kr.co.F1FS.app.domain.file.presentation.dto.ResponseUploadFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface CreateUploadFileUseCase {
    ResponseUploadFileDTO upload(MultipartFile file);
}
