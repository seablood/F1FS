package kr.co.F1FS.app.domain.form.application.port.in.admin.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import org.springframework.data.domain.Page;

public interface AdminPostRoomDeleteFormUseCase {
    Page<ResponsePostRoomDeleteFormListDTO> getPostRoomDeleteFormListByIsConfirmed(int page, int size, String condition, boolean isConfirmed);
    ResponsePostRoomDeleteFormDTO getPostRoomDeleteFormById(Long formId);
    void updateConfirm(UpdatePostRoomDeleteFormDTO dto, Long formId);
}
