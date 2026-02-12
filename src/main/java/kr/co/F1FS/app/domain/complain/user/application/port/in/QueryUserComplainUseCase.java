package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.user.ResponseUserComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.user.SimpleResponseUserComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryUserComplainUseCase {
    Page<SimpleResponseUserComplainDTO> findAllForDTO(Pageable pageable);
    UserComplain findById(Long id);
    ResponseUserComplainDTO findByIdForDTO(Long id);
    List<UserComplain> findAllByToUser(User toUser);
    Page<SimpleResponseUserComplainDTO> findAllByToUserForDTO(User toUser, Pageable pageable);
    Page<SimpleResponseUserComplainDTO> findAllByFromUserForDTO(User fromUser, Pageable pageable);
}
