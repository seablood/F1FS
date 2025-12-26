package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostComplainUseCase {
    void postComplain(Long id, User user, CreatePostComplainDTO dto);
    Page<ResponsePostComplainDTO> findAllByUser(int page, int size, String condition, User user);
    ResponsePostComplainDTO getPostComplain(Long id);
    void delete(Long id, User user);
    Pageable switchCondition(int page, int size, String condition);
}
