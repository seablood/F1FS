package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.port.in.*;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.suggest.presentation.dto.CreateSuggestDTO;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
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
public class ApplicationSuggestService implements SuggestUseCase {
    private final CreateSuggestUseCase createSuggestUseCase;
    private final UpdateSuggestUseCase updateSuggestUseCase;
    private final DeleteSuggestUseCase deleteSuggestUseCase;
    private final QuerySuggestUseCase querySuggestUseCase;

    @Override
    @Transactional
    public ResponseSuggestDTO save(User user, CreateSuggestDTO dto){
        return createSuggestUseCase.save(user, dto);
    }

    @Override
    @Cacheable(value = "SuggestDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseSuggestDTO getSuggestById(Long id){
        return querySuggestUseCase.findByIdForDTO(id);
    }

    @Override
    public Page<ResponseSuggestDTO> getSuggestByUser(int page, int size, User user){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return querySuggestUseCase.findAllByFromUserForDTO(user, pageable);
    }

    @Override
    @Transactional
    public ResponseSuggestDTO modify(Long id, ModifySuggestDTO dto, User user){
        Suggest suggest = querySuggestUseCase.findById(id);

        return updateSuggestUseCase.modify(user, suggest, dto);
    }

    @Override
    @Transactional
    public void delete(Long id, User user){
        Suggest suggest = querySuggestUseCase.findById(id);

        deleteSuggestUseCase.delete(user, suggest);
    }
}
