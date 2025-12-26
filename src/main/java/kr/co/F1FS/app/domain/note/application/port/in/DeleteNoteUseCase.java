package kr.co.F1FS.app.domain.note.application.port.in;

import kr.co.F1FS.app.domain.note.domain.Note;

public interface DeleteNoteUseCase {
    void delete(Note note);
}
