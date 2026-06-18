package kr.co.F1FS.app.domain.form.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRoomFormDSLRepository {
    Optional<PostRoomForm> findById(Long id);
    List<PostRoomForm> findAllBeforeOneMonthAgo();
    Page<ResponsePostRoomFormListDTO> findAllByUser(Long userId, Pageable pageable);
    Page<ResponsePostRoomFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable);
    boolean existsByUser(Long userId);
}
