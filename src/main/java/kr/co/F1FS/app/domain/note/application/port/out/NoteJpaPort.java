package kr.co.F1FS.app.domain.note.application.port.out;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteJpaPort {
    Note save(Note note);
    Note saveAndFlush(Note note);
    Page<Note> findAllByToUser(User user, Pageable pageable);
    Page<Note> findAllByFromUser(User user, Pageable pageable);
    Note findById(Long id);
    void delete(Note note);
}
