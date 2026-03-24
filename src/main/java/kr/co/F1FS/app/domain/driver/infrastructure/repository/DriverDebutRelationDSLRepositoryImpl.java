package kr.co.F1FS.app.domain.driver.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.driver.domain.rdb.QDriver;
import kr.co.F1FS.app.domain.driver.domain.rdb.QDriverDebutRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.dsl.DriverDebutRelationDSLRepository;
import kr.co.F1FS.app.domain.record.domain.QSinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriverDebutRelationDSLRepositoryImpl implements DriverDebutRelationDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<DriverDebutRelation> findByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        QDriverDebutRelation relation = QDriverDebutRelation.driverDebutRelation;
        QDriver driver = QDriver.driver;
        QSinceDebut sinceDebut = QSinceDebut.sinceDebut;

        DriverDebutRelation content = queryFactory
                .selectFrom(relation)
                .join(relation.driverSinceInfo, driver).fetchJoin()
                .join(relation.sinceDebut, sinceDebut).fetchJoin()
                .where(relation.driverSinceInfo.id.eq(driverId), relation.racingClass.eq(racingClass))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public boolean existsByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        QDriverDebutRelation relation = QDriverDebutRelation.driverDebutRelation;

        return queryFactory
                .selectOne()
                .from(relation)
                .where(relation.driverSinceInfo.id.eq(driverId), relation.racingClass.eq(racingClass))
                .fetchFirst() != null;
    }
}
