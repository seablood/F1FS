package kr.co.F1FS.app.domain.suggest.application.port.in;

import kr.co.F1FS.app.domain.suggest.presentation.dto.CreateSuggestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;

public interface CreateSuggestUseCase {
    ResponseSuggestDTO save(User user, CreateSuggestDTO dto);
}
