package kr.co.F1FS.app.domain.complain.note.application.port.in;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.CreateNoteComplainDTO;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateNoteComplainUseCase {
    NoteComplain save(Note note, User user, CreateNoteComplainDTO dto);
}
