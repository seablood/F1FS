package kr.co.F1FS.app.domain.sessionresult.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.constructor.domain.QConstructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.QDriver;
import kr.co.F1FS.app.domain.sessionresult.domain.QSessionResult;
import kr.co.F1FS.app.domain.sessionresult.infrastructure.repository.dsl.SessionResultDSLRepository;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.QResponseSessionResultListDTO;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.ResponseSessionResultListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SessionResultDSLRepositoryImpl implements SessionResultDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ResponseSessionResultListDTO> findAllBySession(Long sessionId) {
        QSessionResult sessionResult = QSessionResult.sessionResult;
        QDriver driver = QDriver.driver;
        QConstructor constructor = QConstructor.constructor;

        List<ResponseSessionResultListDTO> contents = queryFactory
                .select(new QResponseSessionResultListDTO(
                        driver.name,
                        constructor.name,
                        sessionResult.position,
                        sessionResult.timeOrGap,
                        sessionResult.points,
                        sessionResult.isFastestLap,
                        sessionResult.raceStatus
                ))
                .from(sessionResult)
                .join(sessionResult.driver, driver).fetchJoin()
                .join(sessionResult.constructor, constructor).fetchJoin()
                .where(sessionResult.session.id.eq(sessionId))
                .fetch();

        return contents.stream().sorted(Comparator.comparingInt(ResponseSessionResultListDTO::getPosition)).toList();
    }
}
