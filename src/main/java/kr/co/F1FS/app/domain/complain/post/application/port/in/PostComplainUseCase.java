package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostComplainUseCase {
    void save(PostComplain complain);
    void postComplain(Long id, User user, CreatePostComplainDTO dto);
    Page<AdminResponsePostComplainDTO> findAll(Pageable pageable);
}
