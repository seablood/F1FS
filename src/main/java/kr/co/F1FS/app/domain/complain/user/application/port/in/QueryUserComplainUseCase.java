package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.ResponseUserComplainListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.user.ResponseUserComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryUserComplainUseCase {
    Page<ResponseUserComplainListDTO> findUserComplainListForDTO(Pageable pageable);
    UserComplain findByIdWithJoin(Long id);
    ResponseUserComplainDTO findByIdForDTO(Long id);
    List<UserComplain> findAllByToUser(User toUser);
    Page<ResponseUserComplainListDTO> findAllByToUserForDTO(Long userId, Pageable pageable);
    Page<ResponseUserComplainListDTO> findAllByFromUserForDTO(Long userId, Pageable pageable);
}
