package kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostRoomDeleteFormUseCase {
    PostRoomDeleteForm findByIdWithJoin(Long id);
    ResponsePostRoomDeleteFormDTO findByIdWithJoinForDTO(Long id);
    Page<ResponsePostRoomDeleteFormListDTO> findAllByUserForDTO(Long userId, Pageable pageable);
    Page<ResponsePostRoomDeleteFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable);
}
