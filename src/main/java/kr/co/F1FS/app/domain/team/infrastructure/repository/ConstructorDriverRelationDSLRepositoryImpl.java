package kr.co.F1FS.app.domain.team.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.constructor.domain.QConstructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.QDriver;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.team.domain.QConstructorDriverRelation;
import kr.co.F1FS.app.domain.team.infrastructure.repository.dsl.ConstructorDriverRelationDSLRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConstructorDriverRelationDSLRepositoryImpl implements ConstructorDriverRelationDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        QConstructorDriverRelation cdRelation = QConstructorDriverRelation.constructorDriverRelation;

        return queryFactory
                .selectOne()
                .from(cdRelation)
                .where(cdRelation.driver.id.eq(driverId), cdRelation.racingClass.eq(racingClass))
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByDriverAndConstructor(Long driverId, Long constructorId) {
        QConstructorDriverRelation cdRelation = QConstructorDriverRelation.constructorDriverRelation;

        return queryFactory
                .selectOne()
                .from(cdRelation)
                .where(cdRelation.driver.id.eq(driverId), cdRelation.constructor.id.eq(constructorId))
                .fetchFirst() != null;
    }

    @Override
    public ConstructorDriverRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        QConstructorDriverRelation cdRelation = QConstructorDriverRelation.constructorDriverRelation;
        QConstructor constructor = new QConstructor("constructor");

        ConstructorDriverRelation content = queryFactory
                .selectFrom(cdRelation)
                .join(cdRelation.constructor, constructor).fetchJoin()
                .where(cdRelation.driver.id.eq(driverId), cdRelation.racingClass.eq(racingClass))
                .fetchOne();

        return content;
    }

    @Override
    public List<ConstructorDriverRelation> findAllByDriver(Long driverId) {
        QConstructorDriverRelation cdRelation = QConstructorDriverRelation.constructorDriverRelation;
        QConstructor constructor = new QConstructor("constructor");

        List<ConstructorDriverRelation> contents = queryFactory
                .selectFrom(cdRelation)
                .join(cdRelation.constructor, constructor).fetchJoin()
                .where(cdRelation.driver.id.eq(driverId))
                .fetch();

        return contents;
    }

    @Override
    public List<ConstructorDriverRelation> findAllByConstructor(Long constructorId) {
        QConstructorDriverRelation cdRelation = QConstructorDriverRelation.constructorDriverRelation;
        QDriver driver = new QDriver("driver");

        List<ConstructorDriverRelation> contents = queryFactory
                .selectFrom(cdRelation)
                .join(cdRelation.driver, driver).fetchJoin()
                .where(cdRelation.constructor.id.eq(constructorId))
                .fetch();

        return contents;
    }
}
