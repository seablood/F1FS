package kr.co.F1FS.app.domain.admin.suggest.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.admin.suggest.application.port.out.AdminSuggestPort;
import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminSuggestService {
    private final AdminSuggestPort suggestPort;
    private final SuggestUseCase suggestUseCase;
    private final SuggestMapper suggestMapper;
    private final CacheEvictUtil cacheEvictUtil;

    public Page<ResponseSuggestDTO> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return suggestPort.findAll(pageable).map(suggest -> suggestMapper.toResponseSuggestDTO(suggest));
    }

    @Transactional
    public void suggestConfirmedToggle(Long id){
        Suggest suggest = suggestPort.findById(id);
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!suggest.isConfirmed()) suggestUseCase.updateConfirmed(suggest, true);
        else suggestUseCase.updateConfirmed(suggest, false);

        suggestPort.saveAndFlush(suggest);
    }
}
