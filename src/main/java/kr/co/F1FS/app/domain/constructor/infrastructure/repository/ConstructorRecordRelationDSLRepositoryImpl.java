package kr.co.F1FS.app.domain.constructor.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.domain.QConstructor;
import kr.co.F1FS.app.domain.constructor.domain.QConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.dsl.ConstructorRecordRelationDSLRepository;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.QResponseConstructorStandingDTO;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.domain.record.domain.QCurrentSeason;
import kr.co.F1FS.app.domain.record.domain.QSinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConstructorRecordRelationDSLRepositoryImpl implements ConstructorRecordRelationDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ConstructorRecordRelation> findByConstructor(Long constructorId) {
        QConstructorRecordRelation relation = QConstructorRecordRelation.constructorRecordRelation;
        QConstructor constructor = new QConstructor("constructor");
        QCurrentSeason currentSeason = new QCurrentSeason("currentSeason");
        QSinceDebut sinceDebut = new QSinceDebut("sinceDebut");

        ConstructorRecordRelation content = queryFactory
                .selectFrom(relation)
                .join(relation.constructorInfo, constructor).fetchJoin()
                .join(relation.currentSeason, currentSeason).fetchJoin()
                .join(relation.sinceDebut, sinceDebut).fetchJoin()
                .where(relation.constructorInfo.id.eq(constructorId))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public List<ResponseConstructorStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass) {
        QConstructorRecordRelation relation = QConstructorRecordRelation.constructorRecordRelation;
        QConstructor constructor = new QConstructor("constructor");
        QCurrentSeason currentSeason = new QCurrentSeason("currentSeason");

        List<ResponseConstructorStandingDTO> contents = queryFactory
                .select(new QResponseConstructorStandingDTO(
                        constructor.name,
                        currentSeason.championshipPoint
                ))
                .from(relation)
                .join(relation.constructorInfo, constructor).fetchJoin()
                .join(relation.currentSeason, currentSeason).fetchJoin()
                .where(relation.racingClass.eq(racingClass), relation.entryClassSeason.isTrue())
                .fetch();

        return contents.stream().sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints())).toList();
    }

    @Override
    public List<ConstructorRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass) {
        QConstructorRecordRelation relation = QConstructorRecordRelation.constructorRecordRelation;
        QCurrentSeason currentSeason = new QCurrentSeason("currentSeason");

        List<ConstructorRecordRelation> contents = queryFactory
                .selectFrom(relation)
                .join(relation.currentSeason, currentSeason).fetchJoin()
                .where(relation.racingClass.eq(racingClass), relation.entryClassSeason.isTrue())
                .fetch();

        return contents.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getCurrentSeason().getChampionshipPoint(), o1.getCurrentSeason().getChampionshipPoint()))
                .toList();
    }
}
