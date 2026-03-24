package kr.co.F1FS.app.domain.suggest.application.port.in;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ResponseSuggestListDTO;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuerySuggestUseCase {
    Page<ResponseSuggestListDTO> findSuggestListForDTO(Pageable pageable);
    Page<ResponseSuggestListDTO> findAllByUserForDTO(Long userId, Pageable pageable);
    Suggest findById(Long id);
    Suggest findByIdWithJoin(Long id);
    ResponseSuggestDTO findByIdForDTO(Long id);
}
