package kr.co.F1FS.app.domain.post.application.port.in.admin;

import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPostUseCase {
    Page<ResponsePostListDTO> getPostByUser(int page, int size, String condition, Long id);
    ResponsePostDTO getPostById(Long id);
    void delete(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
