package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.application.port.in.UpdateSuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestException;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSuggestService implements UpdateSuggestUseCase {
    private final SuggestJpaPort suggestJpaPort;
    private final SuggestDomainService suggestDomainService;
    private final SuggestMapper suggestMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void updateConfirmed(Suggest suggest) {
        cacheEvictUtil.evictCachingSuggest(suggest);
        suggestDomainService.updateConfirmed(suggest);

        suggestJpaPort.saveAndFlush(suggest);
    }

    @Override
    public ResponseSuggestDTO modify(User user, Suggest suggest, ModifySuggestDTO dto) {
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!suggestDomainService.certification(user, suggest)){
            throw new SuggestException(SuggestExceptionType.NOT_AUTHORITY_UPDATE_SUGGEST);
        }
        suggestDomainService.modify(suggest, dto);
        suggestJpaPort.saveAndFlush(suggest);

        return suggestMapper.toResponseSuggestDTO(suggest);
    }
}
