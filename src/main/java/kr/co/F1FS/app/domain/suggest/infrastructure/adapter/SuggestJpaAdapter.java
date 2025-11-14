package kr.co.F1FS.app.domain.suggest.infrastructure.adapter;

import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.infrastructure.repository.SuggestRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestException;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuggestJpaAdapter implements SuggestJpaPort {
    private final SuggestRepository suggestRepository;
    private final SuggestMapper suggestMapper;

    @Override
    public Suggest save(Suggest suggest) {
        return suggestRepository.save(suggest);
    }

    @Override
    public Suggest saveAndFlush(Suggest suggest) {
        return suggestRepository.saveAndFlush(suggest);
    }

    @Override
    public Page<Suggest> findAll(Pageable pageable) {
        return suggestRepository.findAll(pageable);
    }

    @Override
    public Suggest findById(Long id) {
        return suggestRepository.findById(id)
                .orElseThrow(() -> new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND));
    }

    @Override
    public Page<ResponseSuggestDTO> findAllByFromUser(User user, Pageable pageable) {
        return suggestRepository.findAllByFromUser(user, pageable).map(suggest -> suggestMapper.toResponseSuggestDTO(suggest));
    }

    @Override
    public void delete(Suggest suggest) {
        suggestRepository.delete(suggest);
    }
}
