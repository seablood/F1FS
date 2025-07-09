package kr.co.F1FS.app.domain.complain.post.application.mapper;

import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PostComplainMapper {
    public PostComplain toPostComplain(Post post, User user, CreatePostComplainDTO dto){
        return PostComplain.builder()
                .toPost(post)
                .fromUser(user)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();
    }

    public AdminResponsePostComplainDTO toAdminResponsePostComplainDTO(PostComplain postComplain){
        String fromNickname = postComplain.getFromUser() == null ? "탈퇴한 회원" : postComplain.getFromUser().getNickname();

        return AdminResponsePostComplainDTO.builder()
                .id(postComplain.getId())
                .postId(postComplain.getToPost().getId())
                .postTitle(postComplain.getToPost().getTitle())
                .fromUserNickname(fromNickname)
                .description(postComplain.getDescription())
                .paraphrase(postComplain.getParaphrase())
                .build();
    }
}
