package kr.co.F1FS.app.domain.team.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverTeamPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.mapper.ConstructorDriverRelationMapper;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.team.infrastructure.repository.ConstructorDriverRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverTeamAdapter implements DriverTeamPort {
    private final ConstructorDriverRelationRepository relationRepository;
    private final ConstructorDriverRelationMapper relationMapper;

    @Override
    public void save(ConstructorDriverRelation relation) {
        relationRepository.save(relation);
    }

    @Override
    public ConstructorDriverRelation toConstructorDriverRelation(Constructor constructor, Driver driver) {
        return relationMapper.toConstructorDriverRelation(constructor, driver);
    }
}
