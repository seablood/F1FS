package kr.co.F1FS.app.domain.note.application.port.in;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateNoteUseCase {
    Note save(CreateNoteDTO dto, User toUser, User user);
}
