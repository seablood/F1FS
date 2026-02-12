package kr.co.F1FS.app.domain.tag.application.port.in.tag;

import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.global.presentation.dto.tag.ResponseTagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryTagUseCase {
    Page<ResponseTagDTO> findAllForDTO(Pageable pageable);
    List<Tag> saveTagList(List<String> tags);
}
