package kr.co.F1FS.app.domain.suggest.application.port.in;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuerySuggestUseCase {
    Page<ResponseSuggestDTO> findAllForDTO(Pageable pageable);
    Page<ResponseSuggestDTO> findAllByFromUserForDTO(User user, Pageable pageable);
    Suggest findById(Long id);
    ResponseSuggestDTO findByIdForDTO(Long id);
}
