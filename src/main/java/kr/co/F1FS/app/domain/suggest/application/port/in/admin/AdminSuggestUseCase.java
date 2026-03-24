package kr.co.F1FS.app.domain.suggest.application.port.in.admin;

import kr.co.F1FS.app.domain.suggest.presentation.dto.ResponseSuggestListDTO;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import org.springframework.data.domain.Page;

public interface AdminSuggestUseCase {
    Page<ResponseSuggestListDTO> getSuggestAll(int page, int size);
    ResponseSuggestDTO getSuggestById(Long id);
    void suggestConfirmedToggle(Long id);
}
