package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.post.ResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostComplainUseCase {
    void save(Long id, User user, CreatePostComplainDTO dto);
    Page<ResponsePostComplainListDTO> getPostComplainListByUser(int page, int size, String condition, User user);
    ResponsePostComplainDTO getPostComplainById(Long id);
    void delete(Long id, User user);
    Pageable switchCondition(int page, int size, String condition);
}
