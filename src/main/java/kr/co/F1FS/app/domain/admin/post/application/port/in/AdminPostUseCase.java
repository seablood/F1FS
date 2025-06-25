package kr.co.F1FS.app.domain.admin.post.application.port.in;

import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPostUseCase {
    Page<ResponseSimplePostDTO> getPostByUser(int page, int size, String condition, String nickname);
    Page<AdminResponsePostComplainDTO> getAllComplain(int page, int size, String condition);
    void delete(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
