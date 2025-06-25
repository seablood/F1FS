package kr.co.F1FS.app.domain.admin.suggest.application.port.out;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminSuggestPort {
    Page<Suggest> findAll(Pageable pageable);
    Suggest findById(Long id);
    void saveAndFlush(Suggest suggest);
}
