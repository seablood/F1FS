package kr.co.F1FS.app.domain.tag.application.port.in.tag;

import kr.co.F1FS.app.global.presentation.dto.tag.ResponseTagDTO;
import org.springframework.data.domain.Page;

public interface TagUseCase {
    Page<ResponseTagDTO> getTagList(int page, int size);
}
