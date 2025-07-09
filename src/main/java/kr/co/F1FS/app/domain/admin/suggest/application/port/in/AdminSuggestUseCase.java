package kr.co.F1FS.app.domain.admin.suggest.application.port.in;

import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import org.springframework.data.domain.Page;

public interface AdminSuggestUseCase {
    Page<ResponseSuggestDTO> findAll(int page, int size);
    void suggestConfirmedToggle(Long id);
}
