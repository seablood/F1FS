package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.port.in.DeleteSuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestException;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSuggestService implements DeleteSuggestUseCase {
    private final SuggestJpaPort suggestJpaPort;
    private final SuggestDomainService suggestDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(User user, Suggest suggest) {
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!suggestDomainService.certification(user, suggest)){
            throw new SuggestException(SuggestExceptionType.NOT_AUTHORITY_DELETE_SUGGEST);
        }
        suggestJpaPort.delete(suggest);
    }
}
