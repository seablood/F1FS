package kr.co.F1FS.app.domain.post.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPostUseCase {
    Page<ResponseSimplePostDTO> getPostByUser(int page, int size, String condition, Long id);
    ResponsePostDTO getPostById(Long id);
    void delete(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
