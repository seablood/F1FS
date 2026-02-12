package kr.co.F1FS.app.domain.complain.note.application.service.admin;

import kr.co.F1FS.app.domain.complain.note.application.port.in.QueryNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.in.admin.AdminNoteComplainUseCase;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.SimpleResponseNoteComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAdminNoteComplainService implements AdminNoteComplainUseCase {
    private final QueryNoteComplainUseCase queryNoteComplainUseCase;

    @Override
    public Page<SimpleResponseNoteComplainDTO> getNoteComplainList(int page, int size, String condition) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryNoteComplainUseCase.findAllForDTO(pageable);
    }

    @Override
    @Cacheable(value = "NoteComplainDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseNoteComplainDTO getNoteComplainById(Long id) {
        return queryNoteComplainUseCase.findByIdForDTO(id);
    }

    public Pageable switchCondition(int page, int size, String condition){
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }
}
