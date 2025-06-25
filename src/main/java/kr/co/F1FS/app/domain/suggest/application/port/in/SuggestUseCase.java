package kr.co.F1FS.app.domain.suggest.application.port.in;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.CreateSuggestDTO;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import org.springframework.data.domain.Page;

public interface SuggestUseCase {
    ResponseSuggestDTO save(User user, CreateSuggestDTO dto);
    ResponseSuggestDTO getSuggestById(Long id);
    Page<ResponseSuggestDTO> getSuggestByUser(int page, int size, User user);
    void updateConfirmed(Suggest suggest, boolean isConfirmed);
    ResponseSuggestDTO modify(Long id, ModifySuggestDTO dto, User user);
    void delete(Long id, User user);
}
