package kr.co.F1FS.app.domain.complain.post.application.port.out;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostComplainJpaPort {
    PostComplain save(PostComplain postComplain);
    Page<ResponsePostComplainListDTO> findPostComplainList(Pageable pageable);
    Page<ResponsePostComplainListDTO> findAllByUser(Long userId, Pageable pageable);
    PostComplain findByIdWithJoin(Long id);
    void delete(PostComplain postComplain);
}
