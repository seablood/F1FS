package kr.co.F1FS.app.domain.complain.note.application.port.in;

import kr.co.F1FS.app.domain.complain.note.presentation.dto.CreateNoteComplainDTO;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import org.springframework.data.domain.Page;

public interface NoteComplainUseCase {
    void save(Long id, User user, CreateNoteComplainDTO dto);
    Page<ResponseNoteComplainListDTO> getNoteComplainListByUser(int page, int size, String condition, User user);
    ResponseNoteComplainDTO getNoteComplainById(Long id);
    void delete(Long id, User user);
}
