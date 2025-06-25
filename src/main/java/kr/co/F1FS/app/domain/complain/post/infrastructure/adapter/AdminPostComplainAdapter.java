package kr.co.F1FS.app.domain.complain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.post.application.port.out.AdminPostComplainPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.infrastructure.repository.PostComplainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminPostComplainAdapter implements AdminPostComplainPort {
    private final PostComplainRepository postComplainRepository;

    @Override
    public Page<PostComplain> findAll(Pageable pageable) {
        return postComplainRepository.findAll(pageable);
    }
}
