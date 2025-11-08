package kr.co.F1FS.app.domain.complain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.application.mapper.PostComplainMapper;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.infrastructure.repository.PostComplainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostComplainJpaAdapter implements PostComplainJpaPort {
    private final PostComplainRepository postComplainRepository;
    private final PostComplainMapper postComplainMapper;

    @Override
    public void save(PostComplain postComplain) {
        postComplainRepository.save(postComplain);
    }

    @Override
    public Page<AdminResponsePostComplainDTO> findAll(Pageable pageable) {
        return postComplainRepository.findAll(pageable).map(postComplain -> postComplainMapper.toAdminResponsePostComplainDTO(
                postComplain
        ));
    }
}
