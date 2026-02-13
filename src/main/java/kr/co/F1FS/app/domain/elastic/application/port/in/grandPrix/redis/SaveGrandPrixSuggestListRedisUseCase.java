package kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.redis;

import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseSuggestGrandPrixSearchDTO;

import java.util.List;

public interface SaveGrandPrixSuggestListRedisUseCase {
    boolean hasKey(String keyword);
    List<ResponseSuggestGrandPrixSearchDTO> getSuggestList(String keyword);
    void saveSuggestList(String keyword, List<ResponseSuggestGrandPrixSearchDTO> list);
}
