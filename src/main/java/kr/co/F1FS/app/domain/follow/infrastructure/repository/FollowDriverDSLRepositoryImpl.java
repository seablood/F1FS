package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.driver.domain.rdb.QDriver;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.domain.QFollowDriver;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl.FollowDriverDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowDriverDSLRepositoryImpl implements FollowDriverDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public FollowDriver findByUserAndDriver(Long userId, Long driverId) {
        QFollowDriver followDriver = QFollowDriver.followDriver;

        FollowDriver content = queryFactory
                .selectFrom(followDriver)
                .where(followDriver.followerUser.id.eq(userId), followDriver.followeeDriver.id.eq(driverId))
                .fetchOne();

        return content;
    }

    @Override
    public List<FollowDriver> findAllByUser(Long userId) {
        QFollowDriver followDriver = QFollowDriver.followDriver;
        QDriver driver = QDriver.driver;

        List<FollowDriver> contents = queryFactory
                .selectFrom(followDriver)
                .join(followDriver.followeeDriver, driver).fetchJoin()
                .where(followDriver.followerUser.id.eq(userId))
                .fetch();

        return contents;
    }

    @Override
    public boolean existsByUserAndDriver(Long userId, Long driverId) {
        QFollowDriver followDriver = QFollowDriver.followDriver;

        return queryFactory
                .selectOne()
                .from(followDriver)
                .where(followDriver.followerUser.id.eq(userId), followDriver.followeeDriver.id.eq(driverId))
                .fetchFirst() != null;
    }
}
