package kr.co.F1FS.app.domain.complain.note.application.port.in;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.SimpleResponseNoteComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryNoteComplainUseCase {
    Page<SimpleResponseNoteComplainDTO> findAllForDTO(Pageable pageable);
    NoteComplain findById(Long id);
    ResponseNoteComplainDTO findByIdForDTO(Long id);
    Page<SimpleResponseNoteComplainDTO> findAllByFromUserForDTO(User user, Pageable pageable);
}
