package kr.co.F1FS.app.domain.suggest.application.port.out;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ResponseSuggestListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuggestJpaPort {
    Suggest save(Suggest suggest);
    Suggest saveAndFlush(Suggest suggest);
    Page<ResponseSuggestListDTO> findSuggestList(Pageable pageable);
    Suggest findById(Long id);
    Suggest findByIdWithJoin(Long id);
    Page<ResponseSuggestListDTO> findAllByUser(Long userId, Pageable pageable);
    void delete(Suggest suggest);
}
