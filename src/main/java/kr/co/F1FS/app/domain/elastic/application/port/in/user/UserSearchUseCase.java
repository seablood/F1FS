package kr.co.F1FS.app.domain.elastic.application.port.in.user;

import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDocumentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserSearchUseCase {
    List<ResponseUserDocumentDTO> suggestUser(String keyword);
    Page<ResponseUserDocumentDTO> searchUserWithPaging(int page, int size, String condition, String keyword);
}
