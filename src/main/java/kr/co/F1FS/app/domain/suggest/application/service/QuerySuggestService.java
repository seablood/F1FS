package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.application.port.in.QuerySuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ResponseSuggestListDTO;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuerySuggestService implements QuerySuggestUseCase {
    private final SuggestJpaPort suggestJpaPort;
    private final SuggestMapper suggestMapper;

    @Override
    public Page<ResponseSuggestListDTO> findSuggestListForDTO(Pageable pageable) {
        return suggestJpaPort.findSuggestList(pageable);
    }

    @Override
    public Page<ResponseSuggestListDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return suggestJpaPort.findAllByUser(userId, pageable);
    }

    @Override
    public Suggest findById(Long id) {
        return suggestJpaPort.findById(id);
    }

    @Override
    public Suggest findByIdWithJoin(Long id) {
        return suggestJpaPort.findByIdWithJoin(id);
    }

    @Override
    public ResponseSuggestDTO findByIdForDTO(Long id) {
        return suggestMapper.toResponseSuggestDTO(suggestJpaPort.findByIdWithJoin(id));
    }
}
