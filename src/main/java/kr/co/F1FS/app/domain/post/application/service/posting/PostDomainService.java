package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.CreatePostDTO;
import kr.co.F1FS.app.domain.post.presentation.dto.ModifyPostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDomainService {
    private final PostMapper postMapper;

    public Post createEntity(CreatePostDTO postDTO, User author){
        return postMapper.toPost(postDTO, author);
    }

    public void modify(ModifyPostDTO dto, Post post){
        post.modify(dto);
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
