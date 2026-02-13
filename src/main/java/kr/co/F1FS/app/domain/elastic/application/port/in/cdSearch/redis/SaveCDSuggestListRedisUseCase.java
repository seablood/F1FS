package kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.redis;

import kr.co.F1FS.app.domain.elastic.presentation.dto.CDSearchSuggestionDTO;

import java.util.List;

public interface SaveCDSuggestListRedisUseCase {
    boolean hasKey(String keyword);
    List<CDSearchSuggestionDTO> getSuggestList(String keyword);
    void saveSuggestList(String keyword, List<CDSearchSuggestionDTO> combine);
}
