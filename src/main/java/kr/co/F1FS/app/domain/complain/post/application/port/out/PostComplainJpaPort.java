package kr.co.F1FS.app.domain.complain.post.application.port.out;

import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostComplainJpaPort {
    void save(PostComplain postComplain);
    Page<AdminResponsePostComplainDTO> findAll(Pageable pageable);
}
