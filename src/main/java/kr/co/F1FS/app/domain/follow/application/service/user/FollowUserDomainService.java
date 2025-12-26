package kr.co.F1FS.app.domain.follow.application.service.user;

import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowUserDomainService {
    private final FollowMapper followMapper;

    public FollowUser createEntity(User followerUser, User followeeUser){
        return followMapper.toFollowUser(followerUser, followeeUser);
    }
}
