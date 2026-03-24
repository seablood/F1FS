package kr.co.F1FS.app.domain.note.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.ResponseNoteListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NoteDSLRepository {
    Optional<Note> findById(Long id);
    Page<ResponseNoteListDTO> findAllByToUser(Long userId, Pageable pageable);
    Page<ResponseNoteListDTO> findAllByFromUser(Long userId, Pageable pageable);
}
