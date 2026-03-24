package kr.co.F1FS.app.domain.suggest.infrastructure.adapter;

import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.infrastructure.repository.SuggestRepository;
import kr.co.F1FS.app.domain.suggest.infrastructure.repository.dsl.SuggestDSLRepository;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ResponseSuggestListDTO;
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
    private final SuggestDSLRepository suggestDSLRepository;

    @Override
    public Suggest save(Suggest suggest) {
        return suggestRepository.save(suggest);
    }

    @Override
    public Suggest saveAndFlush(Suggest suggest) {
        return suggestRepository.saveAndFlush(suggest);
    }

    @Override
    public Page<ResponseSuggestListDTO> findSuggestList(Pageable pageable) {
        return suggestDSLRepository.findSuggestList(pageable);
    }

    @Override
    public Suggest findById(Long id) {
        return suggestRepository.findById(id)
                .orElseThrow(() -> new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND));
    }

    @Override
    public Suggest findByIdWithJoin(Long id) {
        return suggestDSLRepository.findById(id)
                .orElseThrow(() -> new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND));
    }

    @Override
    public Page<ResponseSuggestListDTO> findAllByUser(Long userId, Pageable pageable) {
        return suggestDSLRepository.findAllByUser(userId, pageable);
    }

    @Override
    public void delete(Suggest suggest) {
        suggestRepository.delete(suggest);
    }
}
