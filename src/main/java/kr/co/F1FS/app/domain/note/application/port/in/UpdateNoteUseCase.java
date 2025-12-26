package kr.co.F1FS.app.domain.note.application.port.in;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;

public interface UpdateNoteUseCase {
    void modify(Note note, ModifyNoteDTO dto);
    void updateIsRead(Note note);
}
