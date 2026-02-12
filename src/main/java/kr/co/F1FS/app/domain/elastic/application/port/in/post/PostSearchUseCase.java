package kr.co.F1FS.app.domain.elastic.application.port.in.post;

import kr.co.F1FS.app.domain.elastic.presentation.dto.TagListRequestDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDocumentDTO;
import org.springframework.data.domain.Page;

public interface PostSearchUseCase {
    Page<ResponsePostDocumentDTO> getPostList(int page, int size, String condition, String option, String keyword);
    Page<ResponsePostDocumentDTO> getPostListByTags(int page, int size, String condition, TagListRequestDTO dto);
}
