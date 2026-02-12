package kr.co.F1FS.app.domain.elastic.application.service.suggest;

import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.CreateAndUpdateSuggestKeywordSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.SuggestKeywordSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.SuggestKeywordDocument;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAndUpdateSuggestKeywordSearchService implements CreateAndUpdateSuggestKeywordSearchUseCase {
    private final SuggestKeywordSearchRepoPort suggestKeywordSearchRepoPort;
    private final SuggestKeywordSearchDocumentService suggestKeywordSearchDocumentService;
    private final RedisHandler redisHandler;
    private static final String KEY = "search:keywords";

    @Override
    @Transactional
    @Scheduled(fixedRate = 300000)
    public void syncSave() {
        log.info("검색어 점수 부여");

        Set<ZSetOperations.TypedTuple<String>> keywords =
                redisHandler.getStringListRedisTemplate().opsForZSet().reverseRangeWithScores(KEY, 0, 100);

        if(keywords == null || keywords.isEmpty()) return;

        List<SuggestKeywordDocument> documents = new ArrayList<>();

        for (ZSetOperations.TypedTuple<String> tuple : keywords){
            String keyword = tuple.getValue();
            Long count = tuple.getScore().longValue();

            documents.add(suggestKeywordSearchDocumentService.createEntity(keyword, count, LocalDateTime.now()));
            log.info("점수 부여 완료 : {} ({})", keyword, count);
        }

        suggestKeywordSearchRepoPort.saveAll(documents);
    }
}
