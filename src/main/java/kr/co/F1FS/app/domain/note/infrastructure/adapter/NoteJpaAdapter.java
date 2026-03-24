package kr.co.F1FS.app.domain.note.infrastructure.adapter;

import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.infrastructure.repository.NoteRepository;
import kr.co.F1FS.app.domain.note.infrastructure.repository.dsl.NoteDSLRepository;
import kr.co.F1FS.app.domain.note.presentation.dto.ResponseNoteListDTO;
import kr.co.F1FS.app.global.util.exception.note.NoteException;
import kr.co.F1FS.app.global.util.exception.note.NoteExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoteJpaAdapter implements NoteJpaPort {
    private final NoteRepository noteRepository;
    private final NoteDSLRepository noteDSLRepository;

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note saveAndFlush(Note note) {
        return noteRepository.saveAndFlush(note);
    }

    @Override
    public Page<ResponseNoteListDTO> findAllByToUser(Long userId, Pageable pageable) {
        return noteDSLRepository.findAllByToUser(userId, pageable);
    }

    @Override
    public Page<ResponseNoteListDTO> findAllByFromUser(Long userId, Pageable pageable) {
        return noteDSLRepository.findAllByFromUser(userId, pageable);
    }

    @Override
    public Note findByIdWithJoin(Long id) {
        return noteDSLRepository.findById(id)
                .orElseThrow(() -> new NoteException(NoteExceptionType.NOTE_NOT_FOUND));
    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteException(NoteExceptionType.NOTE_NOT_FOUND));
    }

    @Override
    public void delete(Note note) {
        noteRepository.delete(note);
    }
}
