package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.post.application.port.in.posting.CreatePostUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.posting.PostJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.CreatePostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostService implements CreatePostUseCase {
    private final PostJpaPort postJpaPort;
    private final PostDomainService postDomainService;
    private final ValidationService validationService;

    @Override
    public Post save(CreatePostDTO postDTO, User author) {
        Post post = postDomainService.createEntity(postDTO, author);
        validationService.checkValid(post);

        return postJpaPort.save(post);
    }
}
