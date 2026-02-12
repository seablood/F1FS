package kr.co.F1FS.app.domain.complain.note.application.port.in;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteNoteComplainUseCase {
    void delete(NoteComplain noteComplain, User user);
}
