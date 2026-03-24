package kr.co.F1FS.app.domain.complain.note.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NoteComplainDSLRepository {
    Optional<NoteComplain> findById(Long id);
    Page<ResponseNoteComplainListDTO> findNoteComplainList(Pageable pageable);
    Page<ResponseNoteComplainListDTO> findAllByUser(Long userId, Pageable pageable);
}
