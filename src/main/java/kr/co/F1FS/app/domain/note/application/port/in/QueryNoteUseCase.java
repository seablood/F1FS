package kr.co.F1FS.app.domain.note.application.port.in;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.ResponseNoteListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryNoteUseCase {
    Note findById(Long id);
    Note findByIdWithJoin(Long id);
    Page<ResponseNoteListDTO> findAllByToUserFotDTO(Long userId, Pageable pageable);
    Page<ResponseNoteListDTO> findAllByFromUserFotDTO(Long userId, Pageable pageable);
}
