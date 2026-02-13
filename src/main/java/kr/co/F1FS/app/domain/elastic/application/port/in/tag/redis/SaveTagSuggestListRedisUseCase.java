package kr.co.F1FS.app.domain.elastic.application.port.in.tag.redis;

import java.util.List;

public interface SaveTagSuggestListRedisUseCase {
    boolean hasKey(String keyword);
    List<String> getSuggestList(String keyword);
    void saveSuggestList(String keyword, List<String> tags);
}
