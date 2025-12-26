package kr.co.F1FS.app.domain.note.infrastructure.adapter;

import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.infrastructure.repository.NoteRepository;
import kr.co.F1FS.app.domain.user.domain.User;
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

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note saveAndFlush(Note note) {
        return noteRepository.saveAndFlush(note);
    }

    @Override
    public Page<Note> findAllByToUser(User user, Pageable pageable) {
        return noteRepository.findAllByToUser(user, pageable);
    }

    @Override
    public Page<Note> findAllByFromUser(User user, Pageable pageable) {
        return noteRepository.findAllByFromUser(user, pageable);
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
