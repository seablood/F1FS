package kr.co.F1FS.app.domain.user.application.mapper.admin;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.admin.AdminResponseUserDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminUserMapper {
    public AdminResponseUserDTO toAdminResponseUserDTO(User user){
        return AdminResponseUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .followerNum(user.getFollowerNum())
                .followingNum(user.getFollowingNum())
                .providerId(user.getProviderId())
                .provider(user.getProvider())
                .role(user.getRole())
                .description(user.getDescription())
                .createDate(user.getCreateDate())
                .lastLoginDate(user.getLastLoginDate())
                .build();
    }
}
