package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.ResponseUserDTO;
import kr.co.F1FS.app.model.FollowUser;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.FollowUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowUserService {
    private final FollowUserRepository followUserRepository;
    private final UserService userService;

    @Transactional
    public void toggle(User followerUser, String followeeNickname){
        User followeeUser = userService.findByNicknameNotDTO(followeeNickname);

        if(isFollowed(followerUser, followeeUser)){
            FollowUser followUser = followUserRepository.findByFollowerUserAndFolloweeUser(followerUser, followeeUser);
            followUserRepository.delete(followUser);
            followerUser.changeFollowingNum(-1);
            followeeUser.changeFollowerNum(-1);
            return;
        }

        FollowUser followUser = FollowUser.builder()
                .followerUser(followerUser)
                .followeeUser(followeeUser)
                .build();
        followUserRepository.save(followUser);
        followerUser.changeFollowingNum(1);
        followeeUser.changeFollowerNum(1);
    }

    public List<ResponseUserDTO> findFollowers(String nickname){
        User user = userService.findByNicknameNotDTO(nickname);
        return followUserRepository.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(followerUser -> ResponseUserDTO.toDto(followerUser))
                .toList();
    }

    public List<ResponseUserDTO> findFollowees(String nickname){
        User user = userService.findByNicknameNotDTO(nickname);
        return followUserRepository.findByFollowerUser(user).stream()
                .map(followUser -> followUser.getFolloweeUser())
                .map(followeeUser -> ResponseUserDTO.toDto(followeeUser))
                .toList();
    }

    public List<ResponseUserDTO> findFollowersAuth(User user){
        return followUserRepository.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(followerUser -> ResponseUserDTO.toDto(followerUser))
                .toList();
    }

    public List<ResponseUserDTO> findFolloweesAuth(User user){
        return followUserRepository.findByFollowerUser(user).stream()
                .map(followUser -> followUser.getFolloweeUser())
                .map(followeeUser -> ResponseUserDTO.toDto(followeeUser))
                .toList();
    }

    public boolean isFollowed(User followerUser, User followeeUser){
        return followUserRepository.existsFollowUserByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }
}
