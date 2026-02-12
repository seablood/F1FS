package kr.co.F1FS.app.domain.complain.note.application.service;

import kr.co.F1FS.app.domain.complain.note.application.mapper.NoteComplainMapper;
import kr.co.F1FS.app.domain.complain.note.application.port.in.QueryNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.out.NoteComplainJpaPort;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.SimpleResponseNoteComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryNoteComplainService implements QueryNoteComplainUseCase {
    private final NoteComplainJpaPort noteComplainJpaPort;
    private final NoteComplainMapper noteComplainMapper;

    @Override
    public Page<SimpleResponseNoteComplainDTO> findAllForDTO(Pageable pageable) {
        return noteComplainJpaPort.findAll(pageable)
                .map(noteComplain -> noteComplainMapper.toSimpleResponseNoteComplainDTO(noteComplain));
    }

    @Override
    public NoteComplain findById(Long id) {
        return noteComplainJpaPort.findById(id);
    }

    @Override
    public ResponseNoteComplainDTO findByIdForDTO(Long id) {
        return noteComplainMapper.toResponseNoteComplainDTO(noteComplainJpaPort.findById(id));
    }

    @Override
    public Page<SimpleResponseNoteComplainDTO> findAllByFromUserForDTO(User user, Pageable pageable) {
        return noteComplainJpaPort.findAllByFromUser(user, pageable)
                .map(noteComplain -> noteComplainMapper.toSimpleResponseNoteComplainDTO(noteComplain));
    }
}
