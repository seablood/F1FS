package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.team.application.port.in.admin.QueryCDRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryCDRelationService implements QueryCDRelationUseCase {
    private final CDRelationJpaPort relationJpaPort;

    @Override
    public ConstructorDriverRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        return relationJpaPort.findByDriverAndRacingClass(driverId, racingClass);
    }

    @Override
    public List<ConstructorDriverRelation> findAllByConstructor(Long constructorId) {
        return relationJpaPort.findAllByConstructor(constructorId);
    }
}
