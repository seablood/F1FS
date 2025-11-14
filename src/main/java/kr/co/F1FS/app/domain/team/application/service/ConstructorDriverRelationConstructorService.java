package kr.co.F1FS.app.domain.team.application.service;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationConstructorUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorDriverRelationConstructorService implements ConstructorDriverRelationConstructorUseCase {
    private final CDRelationJpaPort relationJpaPort;

    @Override
    public List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor) {
        return relationJpaPort.findConstructorDriverRelationByConstructor(constructor);
    }
}
