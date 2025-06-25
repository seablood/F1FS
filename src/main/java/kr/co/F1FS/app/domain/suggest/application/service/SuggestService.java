package kr.co.F1FS.app.domain.suggest.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.application.port.in.SuggestUseCase;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.suggest.infrastructure.repository.SuggestRepository;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestException;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestExceptionType;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class SuggestService implements SuggestUseCase {
    private final SuggestMapper suggestMapper;
    private final SuggestRepository suggestRepository;
    private final CacheEvictUtil cacheEvictUtil;

    @Transactional
    public ResponseSuggestDTO save(User user, CreateSuggestDTO dto){
        Suggest suggest = suggestRepository.save(suggestMapper.toSuggest(user, dto));
        return suggestMapper.toResponseSuggestDTO(suggest);
    }

    @Cacheable(value = "SuggestDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseSuggestDTO getSuggestById(Long id){
        Suggest suggest = suggestRepository.findById(id)
                .orElseThrow(() -> new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND));
        return suggestMapper.toResponseSuggestDTO(suggest);
    }

    public Page<ResponseSuggestDTO> getSuggestByUser(int page, int size, User user){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return suggestRepository.findAllByFromUser(user, pageable).map(suggest -> suggestMapper.toResponseSuggestDTO(suggest));
    }

    public void updateConfirmed(Suggest suggest, boolean isConfirmed){
        suggest.updateConfirmed(isConfirmed);
    }

    @Transactional
    public ResponseSuggestDTO modify(Long id, ModifySuggestDTO dto, User user){
        Suggest suggest = suggestRepository.findById(id)
                .orElseThrow(() -> new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND));
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!AuthorCertification.certification(user.getUsername(), suggest.getFromUser().getUsername())){
            throw new SuggestException(SuggestExceptionType.NOT_AUTHORITY_UPDATE_SUGGEST);
        }
        suggest.modify(dto);
        suggestRepository.saveAndFlush(suggest);
        return suggestMapper.toResponseSuggestDTO(suggest);
    }

    @Transactional
    public void delete(Long id, User user){
        Suggest suggest = suggestRepository.findById(id)
                .orElseThrow(() -> new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND));
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!AuthorCertification.certification(user.getUsername(), suggest.getFromUser().getUsername())){
            throw new SuggestException(SuggestExceptionType.NOT_AUTHORITY_DELETE_SUGGEST);
        }
        suggestRepository.delete(suggest);
    }
}
