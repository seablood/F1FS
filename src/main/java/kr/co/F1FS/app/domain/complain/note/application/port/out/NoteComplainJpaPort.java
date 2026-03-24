package kr.co.F1FS.app.domain.complain.note.application.port.out;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteComplainJpaPort {
    NoteComplain save(NoteComplain noteComplain);
    Page<ResponseNoteComplainListDTO> findNoteComplainList(Pageable pageable);
    NoteComplain findByIdWithJoin(Long id);
    Page<ResponseNoteComplainListDTO> findAllByUser(Long userId, Pageable pageable);
    void delete(NoteComplain noteComplain);
}
