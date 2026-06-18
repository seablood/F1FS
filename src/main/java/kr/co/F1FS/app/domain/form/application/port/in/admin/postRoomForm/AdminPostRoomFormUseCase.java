package kr.co.F1FS.app.domain.form.application.port.in.admin.postRoomForm;

import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomFormDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import org.springframework.data.domain.Page;

public interface AdminPostRoomFormUseCase {
    Page<ResponsePostRoomFormListDTO> getPostRoomFormListByIsConfirmed(int page, int size, String condition, boolean isConfirmed);
    ResponsePostRoomFormDTO getPostRoomFormById(Long id);
    void updateConfirm(UpdatePostRoomFormDTO dto, Long id);
}
