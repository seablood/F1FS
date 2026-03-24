package kr.co.F1FS.app.domain.suggest.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ResponseSuggestListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SuggestDSLRepository {
    Optional<Suggest> findById(Long id);
    Page<ResponseSuggestListDTO> findSuggestList(Pageable pageable);
    Page<ResponseSuggestListDTO> findAllByUser(Long userId, Pageable pageable);
}
