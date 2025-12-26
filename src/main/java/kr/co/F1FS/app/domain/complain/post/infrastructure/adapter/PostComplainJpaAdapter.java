package kr.co.F1FS.app.domain.complain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.infrastructure.repository.PostComplainRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostComplainJpaAdapter implements PostComplainJpaPort {
    private final PostComplainRepository postComplainRepository;

    @Override
    public PostComplain save(PostComplain postComplain) {
        return postComplainRepository.save(postComplain);
    }

    @Override
    public Page<PostComplain> findAll(Pageable pageable) {
        return postComplainRepository.findAll(pageable);
    }

    @Override
    public Page<PostComplain> findAllByUser(User fromUser, Pageable pageable) {
        return postComplainRepository.findAllByFromUser(fromUser, pageable);
    }

    @Override
    public PostComplain findById(Long id) {
        return postComplainRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_COMPLAIN_NOT_FOUND));
    }

    @Override
    public void delete(PostComplain postComplain) {
        postComplainRepository.delete(postComplain);
    }
}
