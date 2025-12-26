package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponseUserComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserComplainUseCase {
    void userComplain(User user, CreateUserComplainDTO dto);
    ResponseUserComplainDTO getUserComplain(Long id);
    Page<ResponseUserComplainDTO> getUserComplainListByFromUser(int page, int size, String condition, User fromUser);
    void delete(Long id, User user);
    Pageable switchCondition(int page, int size, String condition);
}
