package kr.co.F1FS.app.domain.driver.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.domain.rdb.QDriver;
import kr.co.F1FS.app.domain.driver.domain.rdb.QDriverRecordRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.dsl.DriverRecordRelationDSLRepository;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.QResponseDriverStandingDTO;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
import kr.co.F1FS.app.domain.record.domain.QCurrentSeason;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriverRecordRelationDSLRepositoryImpl implements DriverRecordRelationDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<DriverRecordRelation> findByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        QDriverRecordRelation relation = QDriverRecordRelation.driverRecordRelation;
        QDriver driver = QDriver.driver;
        QCurrentSeason currentSeason = QCurrentSeason.currentSeason;

        DriverRecordRelation content = queryFactory
                .selectFrom(relation)
                .join(relation.driverInfo, driver).fetchJoin()
                .join(relation.currentSeason, currentSeason).fetchJoin()
                .where(relation.driverInfo.id.eq(driverId), relation.racingClass.eq(racingClass))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public List<ResponseDriverStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass) {
        QDriverRecordRelation relation = QDriverRecordRelation.driverRecordRelation;
        QDriver driver = QDriver.driver;
        QCurrentSeason currentSeason = QCurrentSeason.currentSeason;

        List<ResponseDriverStandingDTO> contents = queryFactory
                .select(new QResponseDriverStandingDTO(
                        driver.name,
                        driver.team,
                        currentSeason.championshipPoint
                ))
                .from(relation)
                .join(relation.driverInfo, driver).fetchJoin()
                .join(relation.currentSeason, currentSeason).fetchJoin()
                .where(relation.racingClass.eq(racingClass), relation.entryClassSeason.isTrue())
                .fetch();

        return contents.stream().sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints())).toList();
    }

    @Override
    public List<DriverRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass) {
        QDriverRecordRelation relation = QDriverRecordRelation.driverRecordRelation;
        QCurrentSeason currentSeason = QCurrentSeason.currentSeason;

        List<DriverRecordRelation> contents = queryFactory
                .selectFrom(relation)
                .join(relation.currentSeason, currentSeason).fetchJoin()
                .where(relation.racingClass.eq(racingClass), relation.entryClassSeason.isTrue())
                .fetch();

        return contents.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getCurrentSeason().getChampionshipPoint(), o1.getCurrentSeason().getChampionshipPoint()))
                .toList();
    }
}
