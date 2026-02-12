package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.user.ResponseUserComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.user.SimpleResponseUserComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserComplainUseCase {
    void save(User user, CreateUserComplainDTO dto);
    ResponseUserComplainDTO getUserComplainById(Long id);
    Page<SimpleResponseUserComplainDTO> getUserComplainListByFromUser(int page, int size, String condition, User fromUser);
    void delete(Long id, User user);
    Pageable switchCondition(int page, int size, String condition);
}
