package kr.co.F1FS.app.domain.note.application.port.in;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ResponseNoteListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseNoteDTO;
import org.springframework.data.domain.Page;

public interface NoteUseCase {
    ResponseNoteDTO save(CreateNoteDTO dto, User user, String nickname);
    Page<ResponseNoteListDTO> getNoteListByToUser(User user, int page, int size);
    Page<ResponseNoteListDTO> getNoteListByFromUser(User user, int page, int size);
    ResponseNoteDTO getNoteById(Long id);
    void updateIsRead(Note note);
    ResponseNoteDTO modify(Long id, ModifyNoteDTO dto);
    void delete(Long id);
}
