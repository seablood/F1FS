package kr.co.F1FS.app.domain.suggest.application.port.in;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;

public interface UpdateSuggestUseCase {
    void updateConfirmed(Suggest suggest);
    ResponseSuggestDTO modify(User user, Suggest suggest, ModifySuggestDTO dto);
}
