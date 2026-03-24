package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.domain.QFollowUser;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl.FollowUserDSLRepository;
import kr.co.F1FS.app.domain.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowUserDSLRepositoryImpl implements FollowUserDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public FollowUser findByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId) {
        QFollowUser followUser = QFollowUser.followUser;

        FollowUser content = queryFactory
                .selectFrom(followUser)
                .where(followUser.followerUser.id.eq(followerUserId), followUser.followeeUser.id.eq(followeeUserId))
                .fetchOne();

        return content;
    }

    @Override
    public List<FollowUser> findAllByFollowerUser(Long followerUserId) {
        QFollowUser followUser = QFollowUser.followUser;
        QUser followerUser = new QUser("followerUser");
        QUser followeeUser = new QUser("followeeUser");

        List<FollowUser> contents = queryFactory
                .selectFrom(followUser)
                .join(followUser.followerUser, followerUser).fetchJoin()
                .join(followUser.followeeUser, followeeUser).fetchJoin()
                .where(followUser.followerUser.id.eq(followerUserId))
                .fetch();

        return contents;
    }

    @Override
    public List<FollowUser> findAllByFolloweeUser(Long followeeUserId) {
        QFollowUser followUser = QFollowUser.followUser;
        QUser followerUser = new QUser("followerUser");
        QUser followeeUser = new QUser("followeeUser");

        List<FollowUser> contents = queryFactory
                .selectFrom(followUser)
                .join(followUser.followerUser, followerUser).fetchJoin()
                .join(followUser.followeeUser, followeeUser).fetchJoin()
                .where(followUser.followeeUser.id.eq(followeeUserId))
                .fetch();

        return contents;
    }

    @Override
    public boolean existsByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId) {
        QFollowUser followUser = QFollowUser.followUser;

        return queryFactory
                .selectOne()
                .from(followUser)
                .where(followUser.followerUser.id.eq(followerUserId), followUser.followeeUser.id.eq(followeeUserId))
                .fetchFirst() != null;
    }
}
