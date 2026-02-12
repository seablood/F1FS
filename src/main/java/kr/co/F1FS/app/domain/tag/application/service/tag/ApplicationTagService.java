package kr.co.F1FS.app.domain.tag.application.service.tag;

import kr.co.F1FS.app.domain.tag.application.port.in.tag.QueryTagUseCase;
import kr.co.F1FS.app.domain.tag.application.port.in.tag.TagUseCase;
import kr.co.F1FS.app.global.presentation.dto.tag.ResponseTagDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationTagService implements TagUseCase {
    private final QueryTagUseCase queryTagUseCase;

    @Override
    public Page<ResponseTagDTO> getTagList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));

        return queryTagUseCase.findAllForDTO(pageable);
    }
}
