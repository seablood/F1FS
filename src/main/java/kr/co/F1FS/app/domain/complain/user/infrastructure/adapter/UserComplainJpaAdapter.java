package kr.co.F1FS.app.domain.complain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.application.mapper.UserComplainMapper;
import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.infrastructure.repository.UserComplainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserComplainJpaAdapter implements UserComplainJpaPort {
    private final UserComplainRepository userComplainRepository;
    private final UserComplainMapper userComplainMapper;

    @Override
    public void save(UserComplain userComplain) {
        userComplainRepository.save(userComplain);
    }

    @Override
    public Page<AdminResponseUserComplainDTO> findAll(Pageable pageable) {
        return userComplainRepository.findAll(pageable).map(userComplain -> userComplainMapper.toAdminResponseUserComplainDTO(
                userComplain
        ));
    }
}
