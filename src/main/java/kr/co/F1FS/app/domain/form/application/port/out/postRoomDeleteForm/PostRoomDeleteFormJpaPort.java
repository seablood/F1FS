package kr.co.F1FS.app.domain.form.application.port.out.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRoomDeleteFormJpaPort {
    PostRoomDeleteForm save(PostRoomDeleteForm deleteForm);
    PostRoomDeleteForm saveAndFlush(PostRoomDeleteForm deleteForm);
    PostRoomDeleteForm findByIdWithJoin(Long id);
    List<PostRoomDeleteForm> findAllBeforeOneMonthAgo();
    Page<ResponsePostRoomDeleteFormListDTO> findAllByUser(Long userId, Pageable pageable);
    Page<ResponsePostRoomDeleteFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable);
    boolean existsByPostRoom(Long roomId);
    void delete(PostRoomDeleteForm deleteForm);
    void delete(List<PostRoomDeleteForm> list);
}
