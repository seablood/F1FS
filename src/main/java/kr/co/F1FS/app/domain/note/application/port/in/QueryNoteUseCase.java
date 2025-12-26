package kr.co.F1FS.app.domain.note.application.port.in;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryNoteUseCase {
    Note findById(Long id);
    Page<ResponseSimpleNoteDTO> findAllByToUser(User user, Pageable pageable);
    Page<ResponseSimpleNoteDTO> findAllByFromUser(User user, Pageable pageable);
}
