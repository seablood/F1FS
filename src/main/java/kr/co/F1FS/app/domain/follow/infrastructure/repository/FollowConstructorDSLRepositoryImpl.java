package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.constructor.domain.QConstructor;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.domain.QFollowConstructor;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl.FollowConstructorDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowConstructorDSLRepositoryImpl implements FollowConstructorDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public FollowConstructor findByUserAndConstructor(Long userId, Long constructorId) {
        QFollowConstructor followConstructor = QFollowConstructor.followConstructor;

        FollowConstructor content = queryFactory
                .selectFrom(followConstructor)
                .where(followConstructor.followerUser.id.eq(userId), followConstructor.followeeConstructor.id.eq(constructorId))
                .fetchOne();

        return content;
    }

    @Override
    public List<FollowConstructor> findAllByUser(Long userId) {
        QFollowConstructor followConstructor = QFollowConstructor.followConstructor;
        QConstructor constructor = QConstructor.constructor;

        List<FollowConstructor> contents = queryFactory
                .selectFrom(followConstructor)
                .join(followConstructor.followeeConstructor, constructor).fetchJoin()
                .where(followConstructor.followerUser.id.eq(userId))
                .fetch();

        return contents;
    }

    @Override
    public boolean existsByUserAndConstructor(Long userId, Long constructorId) {
        QFollowConstructor followConstructor = QFollowConstructor.followConstructor;

        return queryFactory
                .selectOne()
                .from(followConstructor)
                .where(followConstructor.followerUser.id.eq(userId), followConstructor.followeeConstructor.id.eq(constructorId))
                .fetchFirst() != null;
    }
}
