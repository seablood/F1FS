package kr.co.F1FS.app.domain.complain.post.application.mapper.admin;

import kr.co.F1FS.app.domain.complain.post.presentation.dto.admin.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import org.springframework.stereotype.Component;

@Component
public class AdminPostComplainMapper {
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
