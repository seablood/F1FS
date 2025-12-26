package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.mapper.NoteMapper;
import kr.co.F1FS.app.domain.note.application.port.in.QueryNoteUseCase;
import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryNoteService implements QueryNoteUseCase {
    private final NoteJpaPort noteJpaPort;
    private final NoteMapper noteMapper;

    @Override
    public Note findById(Long id) {
        return noteJpaPort.findById(id);
    }

    @Override
    public Page<ResponseSimpleNoteDTO> findAllByToUser(User user, Pageable pageable) {
        return noteJpaPort.findAllByToUser(user, pageable).map(note -> {
            String fromNickname = note.getFromUser() == null ? "알 수 없음" : note.getFromUser().getNickname();
            return noteMapper.toResponseSimpleNoteDTO(note, fromNickname+"님으로부터 온 쪽지");
        });
    }

    @Override
    public Page<ResponseSimpleNoteDTO> findAllByFromUser(User user, Pageable pageable) {
        return noteJpaPort.findAllByFromUser(user, pageable).map(note -> {
            String toNickname = note.getToUser() == null ? "알 수 없음" : note.getToUser().getNickname();
            return noteMapper.toResponseSimpleNoteDTO(note, toNickname+"님에게 보낸 쪽지");
        });
    }
}
