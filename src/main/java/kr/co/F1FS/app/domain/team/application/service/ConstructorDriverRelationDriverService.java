package kr.co.F1FS.app.domain.team.application.service;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.mapper.ConstructorDriverRelationMapper;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationDriverUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorDriverRelationDriverService implements ConstructorDriverRelationDriverUseCase {
    private final CDRelationJpaPort relationJpaPort;
    private final ConstructorDriverRelationMapper relationMapper;

    @Override
    public ConstructorDriverRelation save(Constructor constructor, Driver driver) {
        ConstructorDriverRelation relation = relationMapper.toConstructorDriverRelation(constructor, driver);
        return relationJpaPort.save(relation);
    }
}
