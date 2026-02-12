package kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis;

public interface SaveSuggestKeywordSearchRedisUseCase {
    void increaseSearchCount(String keyword);
}
