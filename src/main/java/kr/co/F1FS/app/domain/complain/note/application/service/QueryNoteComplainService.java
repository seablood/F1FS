package kr.co.F1FS.app.domain.complain.note.application.service;

import kr.co.F1FS.app.domain.complain.note.application.mapper.NoteComplainMapper;
import kr.co.F1FS.app.domain.complain.note.application.port.in.QueryNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.out.NoteComplainJpaPort;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
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
    public Page<ResponseNoteComplainListDTO> findNoteComplainListForDTO(Pageable pageable) {
        return noteComplainJpaPort.findNoteComplainList(pageable);
    }

    @Override
    public NoteComplain findByIdWithJoin(Long id) {
        return noteComplainJpaPort.findByIdWithJoin(id);
    }

    @Override
    public ResponseNoteComplainDTO findByIdForDTO(Long id) {
        return noteComplainMapper.toResponseNoteComplainDTO(noteComplainJpaPort.findByIdWithJoin(id));
    }

    @Override
    public Page<ResponseNoteComplainListDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return noteComplainJpaPort.findAllByUser(userId, pageable);
    }
}
