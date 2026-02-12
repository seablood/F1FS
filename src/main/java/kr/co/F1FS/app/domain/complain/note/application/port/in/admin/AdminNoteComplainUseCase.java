package kr.co.F1FS.app.domain.complain.note.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.SimpleResponseNoteComplainDTO;
import org.springframework.data.domain.Page;

public interface AdminNoteComplainUseCase {
    Page<SimpleResponseNoteComplainDTO> getNoteComplainList(int page, int size, String condition);
    ResponseNoteComplainDTO getNoteComplainById(Long id);
}
