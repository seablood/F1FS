package kr.co.F1FS.app.domain.complain.note.application.port.in;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryNoteComplainUseCase {
    Page<ResponseNoteComplainListDTO> findNoteComplainListForDTO(Pageable pageable);
    NoteComplain findByIdWithJoin(Long id);
    ResponseNoteComplainDTO findByIdForDTO(Long id);
    Page<ResponseNoteComplainListDTO> findAllByUserForDTO(Long userId, Pageable pageable);
}
