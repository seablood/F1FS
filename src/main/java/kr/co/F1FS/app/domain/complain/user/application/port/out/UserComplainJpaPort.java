package kr.co.F1FS.app.domain.complain.user.application.port.out;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.ResponseUserComplainListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserComplainJpaPort {
    UserComplain save(UserComplain userComplain);
    Page<ResponseUserComplainListDTO> findUserComplainList(Pageable pageable);
    UserComplain findByIdWithJoin(Long id);
    Page<ResponseUserComplainListDTO> findAllByToUser(Long userId, Pageable pageable);
    Page<ResponseUserComplainListDTO> findAllByFromUser(Long userId, Pageable pageable);
    List<UserComplain> findAllByToUser(User toUser);
    void delete(UserComplain userComplain);
}
