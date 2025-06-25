package kr.co.F1FS.app.domain.complain.post.application.mapper;

import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import org.springframework.stereotype.Component;

@Component
public class PostComplainMapper {
    public AdminResponsePostComplainDTO toAdminResponsePostComplainDTO(PostComplain postComplain){
        return AdminResponsePostComplainDTO.builder()
                .id(postComplain.getId())
                .postId(postComplain.getToPost().getId())
                .postTitle(postComplain.getToPost().getTitle())
                .fromUserNickname(postComplain.getFromUser().getNickname())
                .description(postComplain.getDescription())
                .paraphrase(postComplain.getParaphrase())
                .build();
    }
}
