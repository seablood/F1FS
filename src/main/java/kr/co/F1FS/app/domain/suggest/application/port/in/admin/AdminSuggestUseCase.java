package kr.co.F1FS.app.domain.suggest.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import org.springframework.data.domain.Page;

public interface AdminSuggestUseCase {
    Page<ResponseSuggestDTO> findAll(int page, int size);
    ResponseSuggestDTO findById(Long id);
    void suggestConfirmedToggle(Long id);
}
