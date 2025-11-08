package kr.co.F1FS.app.domain.follow.infrastructure.adapter;

import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowUserRepository;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowUserJpaAdapter implements FollowUserJpaPort {
    private final FollowUserRepository followUserRepository;
    private final FollowMapper followMapper;

    @Override
    public FollowUser save(FollowUser followUser) {
        return followUserRepository.save(followUser);
    }

    @Override
    public FollowUser findByFollowerUserAndFolloweeUser(User followerUser, User followeeUser) {
        return followUserRepository.findByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }

    @Override
    public List<ResponseFollowUserDTO> findByFolloweeUser(User user) {
        return followUserRepository.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(user1 -> followMapper.toResponseFollowUserDTO(user1))
                .toList();
    }

    @Override
    public List<ResponseFollowUserDTO> findByFollowerUser(User user) {
        return followUserRepository.findByFollowerUser(user).stream()
                .map(followUser -> followUser.getFolloweeUser())
                .map(user1 -> followMapper.toResponseFollowUserDTO(user1))
                .toList();
    }

    @Override
    public List<ResponseUserIdDTO> findByFolloweeUserId(User user) {
        return followUserRepository.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(user1 -> followMapper.toResponseUserIdDTO(user1))
                .toList();
    }

    @Override
    public boolean existsFollowUserByFollowerUserAndFolloweeUser(User followerUser, User followeeUser) {
        return followUserRepository.existsFollowUserByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }

    @Override
    public void delete(FollowUser followUser) {
        followUserRepository.delete(followUser);
    }
}
