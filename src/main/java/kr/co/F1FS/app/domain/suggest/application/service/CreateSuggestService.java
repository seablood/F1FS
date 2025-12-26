package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.application.port.in.CreateSuggestUseCase;
import kr.co.F1FS.app.domain.suggest.application.port.out.SuggestJpaPort;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.CreateSuggestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSuggestService implements CreateSuggestUseCase {
    private final SuggestJpaPort suggestJpaPort;
    private final SuggestDomainService suggestDomainService;
    private final SuggestMapper suggestMapper;

    @Override
    public ResponseSuggestDTO save(User user, CreateSuggestDTO dto) {
        Suggest suggest = suggestJpaPort.save(suggestDomainService.createEntity(user, dto));

        return suggestMapper.toResponseSuggestDTO(suggest);
    }
}
