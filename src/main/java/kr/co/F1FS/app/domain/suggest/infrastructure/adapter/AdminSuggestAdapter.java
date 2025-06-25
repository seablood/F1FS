package kr.co.F1FS.app.domain.suggest.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.suggest.application.port.out.AdminSuggestPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.infrastructure.repository.SuggestRepository;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestException;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSuggestAdapter implements AdminSuggestPort {
    private final SuggestRepository suggestRepository;

    @Override
    public Page<Suggest> findAll(Pageable pageable) {
        return suggestRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "Suggest", key = "#id", cacheManager = "redisLongCacheManager")
    public Suggest findById(Long id) {
        return suggestRepository.findById(id)
                .orElseThrow(() -> new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND));
    }

    @Override
    public void saveAndFlush(Suggest suggest) {
        suggestRepository.saveAndFlush(suggest);
    }
}
