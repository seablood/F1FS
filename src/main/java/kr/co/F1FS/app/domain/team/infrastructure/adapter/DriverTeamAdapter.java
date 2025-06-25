package kr.co.F1FS.app.domain.team.infrastructure.adapter;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverTeamPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.team.infrastructure.repository.ConstructorDriverRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverTeamAdapter implements DriverTeamPort {
    private final ConstructorDriverRelationRepository relationRepository;

    @Override
    @Transactional
    public void save(ConstructorDriverRelation relation) {
        relationRepository.save(relation);
    }
}
