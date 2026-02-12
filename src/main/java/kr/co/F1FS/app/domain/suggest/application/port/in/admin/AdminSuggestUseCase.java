package kr.co.F1FS.app.domain.suggest.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import kr.co.F1FS.app.global.presentation.dto.suggest.SimpleResponseSuggestDTO;
import org.springframework.data.domain.Page;

public interface AdminSuggestUseCase {
    Page<SimpleResponseSuggestDTO> getSuggestAll(int page, int size);
    ResponseSuggestDTO getSuggestById(Long id);
    void suggestConfirmedToggle(Long id);
}
