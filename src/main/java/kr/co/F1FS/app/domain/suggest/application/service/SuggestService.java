package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.application.port.in.SuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuggestService implements SuggestUseCase {
    private final SuggestMapper suggestMapper;
    private final SuggestJpaPort suggestJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public ResponseSuggestDTO save(User user, CreateSuggestDTO dto){
        Suggest suggest = suggestJpaPort.save(suggestMapper.toSuggest(user, dto));
        return suggestMapper.toResponseSuggestDTO(suggest);
    }

    @Override
    public Suggest saveAndFlush(Suggest suggest) {
        return suggestJpaPort.saveAndFlush(suggest);
    }

    @Override
    public Page<Suggest> findAll(Pageable pageable) {
        return suggestJpaPort.findAll(pageable);
    }

    @Override
    public Suggest findByIdNotDTONotCache(Long id) {
        return suggestJpaPort.findById(id);
    }

    @Override
    @Cacheable(value = "SuggestDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseSuggestDTO getSuggestById(Long id){
        Suggest suggest = suggestJpaPort.findById(id);
        return suggestMapper.toResponseSuggestDTO(suggest);
    }

    @Override
    public Page<ResponseSuggestDTO> getSuggestByUser(int page, int size, User user){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return suggestJpaPort.findAllByFromUser(user, pageable);
    }

    @Override
    public void updateConfirmed(Suggest suggest, boolean isConfirmed){
        cacheEvictUtil.evictCachingSuggest(suggest);
        suggest.updateConfirmed(isConfirmed);

        suggestJpaPort.saveAndFlush(suggest);
    }

    @Override
    @Transactional
    public ResponseSuggestDTO modify(Long id, ModifySuggestDTO dto, User user){
        Suggest suggest = suggestJpaPort.findById(id);
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!AuthorCertification.certification(user.getUsername(), suggest.getFromUser().getUsername())){
            throw new SuggestException(SuggestExceptionType.NOT_AUTHORITY_UPDATE_SUGGEST);
        }
        suggest.modify(dto);
        suggestJpaPort.saveAndFlush(suggest);
        return suggestMapper.toResponseSuggestDTO(suggest);
    }

    @Override
    @Transactional
    public void delete(Long id, User user){
        Suggest suggest = suggestJpaPort.findById(id);
        cacheEvictUtil.evictCachingSuggest(suggest);

        if(!AuthorCertification.certification(user.getUsername(), suggest.getFromUser().getUsername())){
            throw new SuggestException(SuggestExceptionType.NOT_AUTHORITY_DELETE_SUGGEST);
        }
        suggestJpaPort.delete(suggest);
    }

    @Override
    public ResponseSuggestDTO toResponseSuggestDTO(Suggest suggest) {
        return suggestMapper.toResponseSuggestDTO(suggest);
    }
}
