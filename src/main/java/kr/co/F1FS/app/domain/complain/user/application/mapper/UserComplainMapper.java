package kr.co.F1FS.app.domain.complain.user.application.mapper;

import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import org.springframework.stereotype.Component;

@Component
public class UserComplainMapper {
    public AdminResponseUserComplainDTO toAdminResponseUserComplainDTO(UserComplain userComplain){
        return AdminResponseUserComplainDTO.builder()
                .id(userComplain.getId())
                .toUserNickname(userComplain.getToUser().getNickname())
                .fromUserNickname(userComplain.getFromUser().getNickname())
                .description(userComplain.getDescription())
                .paraphrase(userComplain.getParaphrase())
                .build();
    }
}
