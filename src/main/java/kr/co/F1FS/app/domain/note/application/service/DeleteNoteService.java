package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.port.in.DeleteNoteUseCase;
import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.note.NoteException;
import kr.co.F1FS.app.global.util.exception.note.NoteExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteNoteService implements DeleteNoteUseCase {
    private final NoteJpaPort noteJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(Note note) {
        if(note.isRead()){
            throw new NoteException(NoteExceptionType.NOTE_IS_READ);
        }
        cacheEvictUtil.evictCachingNote(note);

        noteJpaPort.delete(note);
    }
}
