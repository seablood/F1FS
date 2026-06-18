package kr.co.F1FS.app.domain.form.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRoomDeleteFormDSLRepository {
    Optional<PostRoomDeleteForm> findById(Long id);
    List<PostRoomDeleteForm> findAllBeforeOneMonthAgo();
    Page<ResponsePostRoomDeleteFormListDTO> findAllByUser(Long userId, Pageable pageable);
    Page<ResponsePostRoomDeleteFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable);
    boolean existsByPostRoom(Long roomId);
}
