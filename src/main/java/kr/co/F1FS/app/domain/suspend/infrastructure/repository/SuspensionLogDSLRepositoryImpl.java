package kr.co.F1FS.app.domain.suspend.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.suspend.domain.QSuspensionLog;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.infrastructure.repository.dsl.SuspensionLogDSLRepository;
import kr.co.F1FS.app.domain.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SuspensionLogDSLRepositoryImpl implements SuspensionLogDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public SuspensionLog findNyUser(Long userId) {
        QSuspensionLog suspensionLog = QSuspensionLog.suspensionLog;
        QUser suspendUser = new QUser("suspendUser");

        SuspensionLog content = queryFactory
                .selectFrom(suspensionLog)
                .join(suspensionLog.suspendUser, suspendUser).fetchJoin()
                .where(suspensionLog.suspendUser.id.eq(userId))
                .fetchOne();

        return content;
    }
}
