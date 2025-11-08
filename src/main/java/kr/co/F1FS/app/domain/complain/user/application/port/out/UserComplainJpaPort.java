package kr.co.F1FS.app.domain.complain.user.application.port.out;

import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserComplainJpaPort {
    void save(UserComplain userComplain);
    Page<AdminResponseUserComplainDTO> findAll(Pageable pageable);
}
