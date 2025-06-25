package kr.co.F1FS.app.domain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.post.application.port.out.AdminPostPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.infrastructure.repository.PostRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminPostAdapter implements AdminPostPort {
    private final PostRepository postRepository;

    @Override
    public Page<Post> findAllByAuthor(User user, Pageable pageable) {
        return postRepository.findAllByAuthor(user, pageable);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
