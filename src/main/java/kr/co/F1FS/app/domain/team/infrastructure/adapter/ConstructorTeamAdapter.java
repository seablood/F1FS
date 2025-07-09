package kr.co.F1FS.app.domain.team.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.application.port.out.ConstructorTeamPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.team.infrastructure.repository.ConstructorDriverRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConstructorTeamAdapter implements ConstructorTeamPort {
    private final ConstructorDriverRelationRepository relationRepository;

    @Override
    public List<ConstructorDriverRelation> getDrivers(Constructor constructor) {
        return relationRepository.findConstructorDriverRelationByConstructor(constructor);
    }
}
