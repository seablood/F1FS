package kr.co.F1FS.app.domain.form.application.port.out.postRoomForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRoomFormJpaPort {
    PostRoomForm save(PostRoomForm postRoomForm);
    PostRoomForm saveAndFlush(PostRoomForm postRoomForm);
    PostRoomForm findByIdWithJoin(Long id);
    List<PostRoomForm> findAllBeforeOneMonthAgo();
    Page<ResponsePostRoomFormListDTO> findAllByUser(Long userId, Pageable pageable);
    Page<ResponsePostRoomFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable);
    boolean existsByUser(Long userId);
    void delete(PostRoomForm postRoomForm);
    void deleteAll(List<PostRoomForm> list);
}
