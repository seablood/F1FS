package kr.co.F1FS.app.domain.suggest.application.port.out;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuggestJpaPort {
    Suggest save(Suggest suggest);
    Suggest saveAndFlush(Suggest suggest);
    Page<Suggest> findAll(Pageable pageable);
    Suggest findById(Long id);
    Page<ResponseSuggestDTO> findAllByFromUser(User user, Pageable pageable);
    void delete(Suggest suggest);
}
