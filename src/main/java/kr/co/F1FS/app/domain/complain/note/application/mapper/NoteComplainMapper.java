package kr.co.F1FS.app.domain.complain.note.application.mapper;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.CreateNoteComplainDTO;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import org.springframework.stereotype.Component;

@Component
public class NoteComplainMapper {
    public NoteComplain toNoteComplain(Note toNote, User fromUser, CreateNoteComplainDTO dto){
        return NoteComplain.builder()
                .toNote(toNote)
                .fromUser(fromUser)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();
    }

    public ResponseNoteComplainDTO toResponseNoteComplainDTO(NoteComplain noteComplain){
        return ResponseNoteComplainDTO.builder()
                .id(noteComplain.getId())
                .noteId(noteComplain.getToNote().getId())
                .noteContent(noteComplain.getToNote().getContent())
                .fromUserNickname(noteComplain.getFromUser().getNickname())
                .description(noteComplain.getDescription())
                .paraphrase(noteComplain.getParaphrase())
                .build();
    }
}
