package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.application.port.in.QuerySuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import kr.co.F1FS.app.global.presentation.dto.suggest.SimpleResponseSuggestDTO;
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
    public Page<SimpleResponseSuggestDTO> findAllForDTO(Pageable pageable) {
        return suggestJpaPort.findAll(pageable).map(suggest -> suggestMapper.toSimpleResponseSuggestDTO(suggest));
    }

    @Override
    public Page<SimpleResponseSuggestDTO> findAllByFromUserForDTO(User user, Pageable pageable) {
        return suggestJpaPort.findAllByFromUser(user, pageable).map(suggest -> suggestMapper.toSimpleResponseSuggestDTO(suggest));
    }

    @Override
    public Suggest findById(Long id) {
        return suggestJpaPort.findById(id);
    }

    @Override
    public ResponseSuggestDTO findByIdForDTO(Long id) {
        return suggestMapper.toResponseSuggestDTO(suggestJpaPort.findById(id));
    }
}
