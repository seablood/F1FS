package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.port.in.QueryNoteUseCase;
import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.ResponseNoteListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryNoteService implements QueryNoteUseCase {
    private final NoteJpaPort noteJpaPort;

    @Override
    public Note findById(Long id) {
        return noteJpaPort.findById(id);
    }

    @Override
    public Note findByIdWithJoin(Long id) {
        return noteJpaPort.findByIdWithJoin(id);
    }

    @Override
    public Page<ResponseNoteListDTO> findAllByToUserFotDTO(Long userId, Pageable pageable) {
        return noteJpaPort.findAllByToUser(userId, pageable);
    }

    @Override
    public Page<ResponseNoteListDTO> findAllByFromUserFotDTO(Long userId, Pageable pageable) {
        return noteJpaPort.findAllByFromUser(userId, pageable);
    }
}
