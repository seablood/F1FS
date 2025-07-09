package kr.co.F1FS.app.domain.complain.user.application.mapper;

import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserComplainMapper {
    public UserComplain toUserComplain(CreateUserComplainDTO dto, User toUser, User fromUser){
        return UserComplain.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();
    }

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
