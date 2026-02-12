package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.in.UpdateGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixJpaPort;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateGrandPrixService implements UpdateGrandPrixUseCase {
    private final GrandPrixJpaPort grandPrixJpaPort;
    private final GrandPrixDomainService grandPrixDomainService;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;
    private final CacheEvictUtil cacheEvictUtil;
    private final ValidationService validationService;

    @Override
    public void modify(ModifyGrandPrixCommand command, GrandPrix grandPrix) {
        cacheEvictUtil.evictCachingGrandPrix(grandPrix);
        grandPrixDomainService.modify(command, grandPrix);
        validationService.checkValid(grandPrix);

        saveSuggestKeyword(grandPrix);
        grandPrixJpaPort.saveAndFlush(grandPrix);
    }

    @Override
    public void setSessionPart(GrandPrix grandPrix, Long sessionId, SessionType sessionType) {
        grandPrixDomainService.setSessionId(grandPrix, sessionId, sessionType);

        grandPrixJpaPort.saveAndFlush(grandPrix);
    }

    public void saveSuggestKeyword(GrandPrix grandPrix){
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(grandPrix.getName());
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(grandPrix.getEngName());
    }
}
