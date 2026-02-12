package kr.co.F1FS.app.domain.complain.note.application.service;

import kr.co.F1FS.app.domain.complain.note.application.mapper.NoteComplainMapper;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.CreateNoteComplainDTO;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.exception.note.NoteException;
import kr.co.F1FS.app.global.util.exception.note.NoteExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteComplainDomainService {
    private final NoteComplainMapper noteComplainMapper;

    public NoteComplain createEntity(Note note, User user, CreateNoteComplainDTO dto){
        return noteComplainMapper.toNoteComplain(note, user, dto);
    }

    public boolean certification(NoteComplain noteComplain, User user){
        if(AuthorCertification.certification(user.getUsername(), noteComplain.getFromUser().getUsername())){
            return true;
        }else {
            throw new NoteException(NoteExceptionType.NOT_AUTHORITY_DELETE_NOTE_COMPLAIN);
        }
    }
}
