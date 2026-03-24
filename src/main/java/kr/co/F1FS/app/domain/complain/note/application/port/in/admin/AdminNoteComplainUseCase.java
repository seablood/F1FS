package kr.co.F1FS.app.domain.complain.note.application.port.in.admin;

import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import org.springframework.data.domain.Page;

public interface AdminNoteComplainUseCase {
    Page<ResponseNoteComplainListDTO> getNoteComplainList(int page, int size, String condition);
    ResponseNoteComplainDTO getNoteComplainById(Long id);
}
