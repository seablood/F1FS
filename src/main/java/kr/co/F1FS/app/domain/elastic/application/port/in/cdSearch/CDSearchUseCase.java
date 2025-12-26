package kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch;

import kr.co.F1FS.app.domain.elastic.presentation.dto.CDSearchSuggestionDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CDSearchUseCase {
    List<CDSearchSuggestionDTO> suggestCD(String keyword);
    Page<CDSearchSuggestionDTO> searchCDWithPaging(int page, int size, String condition, String keyword);
}
