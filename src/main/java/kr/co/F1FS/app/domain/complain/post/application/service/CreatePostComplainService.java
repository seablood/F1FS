package kr.co.F1FS.app.domain.complain.post.application.service;

import kr.co.F1FS.app.domain.complain.post.application.port.in.CreatePostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostComplainService implements CreatePostComplainUseCase {
    private final PostComplainJpaPort postComplainJpaPort;
    private final PostComplainDomainService postComplainDomainService;

    @Override
    public PostComplain save(Post post, User user, CreatePostComplainDTO dto) {
        PostComplain postComplain = postComplainDomainService.createEntity(post, user, dto);
        return postComplainJpaPort.save(postComplain);
    }
}
