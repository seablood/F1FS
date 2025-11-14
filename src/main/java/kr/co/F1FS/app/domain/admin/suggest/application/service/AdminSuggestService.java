package kr.co.F1FS.app.domain.admin.suggest.application.service;

import kr.co.F1FS.app.domain.admin.suggest.application.port.in.AdminSuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.in.SuggestUseCase;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminSuggestService implements AdminSuggestUseCase {
    private final SuggestUseCase suggestUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public Page<ResponseSuggestDTO> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return suggestUseCase.findAll(pageable).map(suggest -> suggestUseCase.toResponseSuggestDTO(suggest));
    }

    @Override
    @Transactional
    public void suggestConfirmedToggle(Long id){
        Suggest suggest = suggestUseCase.findByIdNotDTONotCache(id);
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!suggest.isConfirmed()) suggestUseCase.updateConfirmed(suggest, true);
        else suggestUseCase.updateConfirmed(suggest, false);

        suggestUseCase.saveAndFlush(suggest);
    }
}
