package kr.co.F1FS.app.domain.note.application.port.in;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseNoteDTO;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import org.springframework.data.domain.Page;

public interface NoteUseCase {
    ResponseNoteDTO save(CreateNoteDTO dto, User user, String nickname);
    Page<ResponseSimpleNoteDTO> getNoteByToUser(User user, int page, int size);
    Page<ResponseSimpleNoteDTO> getNoteByFromUser(User user, int page, int size);
    Note findByIdNotDTO(Long id);
    ResponseNoteDTO findByIdDTO(Long id);
    void updateIsRead(Note note);
    ResponseNoteDTO modify(Long id, ModifyNoteDTO dto);
    void delete(Long id);
}
