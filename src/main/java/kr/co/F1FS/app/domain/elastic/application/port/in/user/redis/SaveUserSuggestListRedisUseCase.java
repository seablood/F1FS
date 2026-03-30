package kr.co.F1FS.app.domain.elastic.application.port.in.user.redis;

import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDocumentDTO;

import java.util.List;

public interface SaveUserSuggestListRedisUseCase {
    boolean hasKey(String keyword);
    List<ResponseUserDocumentDTO> getSuggestList(String keyword);
    void saveSuggestList(String keyword, List<ResponseUserDocumentDTO> list);
}
