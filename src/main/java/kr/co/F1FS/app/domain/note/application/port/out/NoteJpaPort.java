package kr.co.F1FS.app.domain.note.application.port.out;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.ResponseNoteListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteJpaPort {
    Note save(Note note);
    Note saveAndFlush(Note note);
    Page<ResponseNoteListDTO> findAllByToUser(Long userId, Pageable pageable);
    Page<ResponseNoteListDTO> findAllByFromUser(Long userId, Pageable pageable);
    Note findByIdWithJoin(Long id);
    Note findById(Long id);
    void delete(Note note);
}
