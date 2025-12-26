package kr.co.F1FS.app.domain.complain.user.application.mapper.admin;

import kr.co.F1FS.app.domain.complain.user.presentation.dto.admin.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import org.springframework.stereotype.Component;

@Component
public class AdminUserComplainMapper {
    public AdminResponseUserComplainDTO toAdminResponseUserComplainDTO(UserComplain userComplain){
        String fromNickname = userComplain.getFromUser() == null ? "탈퇴한 회원" : userComplain.getFromUser().getNickname();

        return AdminResponseUserComplainDTO.builder()
                .id(userComplain.getId())
                .toUserNickname(userComplain.getToUser().getNickname())
                .fromUserNickname(fromNickname)
                .description(userComplain.getDescription())
                .paraphrase(userComplain.getParaphrase())
                .build();
    }
}
