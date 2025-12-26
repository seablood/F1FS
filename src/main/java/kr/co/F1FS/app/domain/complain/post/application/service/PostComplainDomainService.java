package kr.co.F1FS.app.domain.complain.post.application.service;

import kr.co.F1FS.app.domain.complain.post.application.mapper.PostComplainMapper;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostComplainDomainService {
    private final PostComplainMapper postComplainMapper;

    public PostComplain createEntity(Post post, User user, CreatePostComplainDTO dto){
        return postComplainMapper.toPostComplain(post, user, dto);
    }

    public boolean certification(PostComplain postComplain, User user){
        if(AuthorCertification.certification(user.getUsername(), postComplain.getFromUser().getUsername())){
            return true;
        }else {
            throw new PostException(PostExceptionType.NOT_AUTHORITY_DELETE_POST_COMPLAIN);
        }
    }
}
