package kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis;

import java.util.List;

public interface SaveSuggestListRedisUseCase {
    boolean hasKey(String keyword);
    List<String> getSuggestList(String keyword);
    void saveSuggestList(String keyword, List<String> keywords);
}
