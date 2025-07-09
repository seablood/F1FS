package kr.co.F1FS.app.domain.note.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

public interface NoteUserPort {
    User findByNicknameNotDTO(String nickname);
}
