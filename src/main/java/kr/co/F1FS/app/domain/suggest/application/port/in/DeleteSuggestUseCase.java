package kr.co.F1FS.app.domain.suggest.application.port.in;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteSuggestUseCase {
    void delete(User user, Suggest suggest);
}
