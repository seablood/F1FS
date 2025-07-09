package kr.co.F1FS.app.domain.note.application.mapper;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseNoteDTO;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import kr.co.F1FS.app.global.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NoteMapper {
    public Note toNote(CreateNoteDTO dto, User toUser, User fromUser){
        return Note.builder()
                .content(dto.getContent())
                .toUser(toUser)
                .fromUser(fromUser)
                .build();
    }

    public ResponseNoteDTO toResponseNoteDTO(Note note){
        String toNickname = note.getToUser() == null ? "알 수 없음" : note.getToUser().getNickname();
        String fromNickname = note.getFromUser() == null ? "알 수 없음" : note.getFromUser().getNickname();

        return ResponseNoteDTO.builder()
                .content(note.getContent())
                .toNickname(toNickname)
                .fromNickname(fromNickname)
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }

    public ResponseSimpleNoteDTO toResponseSimpleNoteDTO(Note note, String simpleContent){
        LocalDateTime noteTime = TimeUtil.convertToKoreanTime(note.getCreatedAt());

        return ResponseSimpleNoteDTO.builder()
                .id(note.getId())
                .simpleContent(simpleContent)
                .createdAt(TimeUtil.formatPostTime(noteTime))
                .isRead(note.isRead())
                .build();
    }
}
