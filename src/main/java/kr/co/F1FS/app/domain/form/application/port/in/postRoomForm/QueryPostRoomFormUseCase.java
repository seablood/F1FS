package kr.co.F1FS.app.domain.form.application.port.in.postRoomForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostRoomFormUseCase {
    PostRoomForm findByIdWithJoin(Long id);
    ResponsePostRoomFormDTO findByIdWithJoinForDTO(Long id);
    Page<ResponsePostRoomFormListDTO> findAllByUserForDTO(Long userId, Pageable pageable);
    Page<ResponsePostRoomFormListDTO> findAllByIsConfirmedForDTO(boolean isConfirmed, Pageable pageable);
}
