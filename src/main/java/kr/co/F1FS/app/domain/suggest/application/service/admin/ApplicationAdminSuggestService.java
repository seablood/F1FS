package kr.co.F1FS.app.domain.suggest.application.service.admin;

import kr.co.F1FS.app.domain.suggest.application.port.in.admin.AdminSuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.in.QuerySuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.in.UpdateSuggestUseCase;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationAdminSuggestService implements AdminSuggestUseCase {
    private final UpdateSuggestUseCase updateSuggestUseCase;
    private final QuerySuggestUseCase querySuggestUseCase;

    @Override
    public Page<ResponseSuggestDTO> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return querySuggestUseCase.findAllForDTO(pageable);
    }

    @Override
    @Cacheable(value = "SuggestDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseSuggestDTO findById(Long id) {
        return querySuggestUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional
    public void suggestConfirmedToggle(Long id){
        Suggest suggest = querySuggestUseCase.findById(id);

        updateSuggestUseCase.updateConfirmed(suggest);
    }
}
