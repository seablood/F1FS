package kr.co.F1FS.app.domain.complain.user.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.ResponseUserComplainListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserComplainDSLRepository {
    Optional<UserComplain> findById(Long id);
    Page<ResponseUserComplainListDTO> findUserComplainList(Pageable pageable);
    Page<ResponseUserComplainListDTO> findAllByFromUser(Long userId, Pageable pageable);
    Page<ResponseUserComplainListDTO> findAllByToUser(Long userId, Pageable pageable);
}
