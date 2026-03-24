package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.post.application.mapper.posting.PostMapper;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostBlock;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostDomainService {
    private final PostMapper postMapper;

    public Post createEntity(String title, User author){
        return postMapper.toPost(title, author);
    }

    public void modify(String title, List<PostBlock> newBlock, Post post){
        post.update(title, newBlock);
    }

    public void increaseLike(Post post){
        post.increaseLike();
    }

    public void decreaseLike(Post post){
        post.decreaseLike();
    }

    public boolean certification(User user, Post post){
        return AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername());
    }
}
