package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.mapper.NoteMapper;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteDomainService {
    private final NoteMapper noteMapper;

    public Note createEntity(CreateNoteDTO dto, User toUser, User user){
        return noteMapper.toNote(dto, toUser, user);
    }

    public void updateIsRead(Note note){
        note.updateIsRead();
    }

    public void modify(Note note, ModifyNoteDTO dto){
        note.modify(dto);
    }
}
