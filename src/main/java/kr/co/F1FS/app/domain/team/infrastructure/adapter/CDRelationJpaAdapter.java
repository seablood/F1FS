package kr.co.F1FS.app.domain.team.infrastructure.adapter;

import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.team.infrastructure.repository.ConstructorDriverRelationRepository;
import kr.co.F1FS.app.domain.team.infrastructure.repository.dsl.ConstructorDriverRelationDSLRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CDRelationJpaAdapter implements CDRelationJpaPort {
    private final ConstructorDriverRelationRepository relationRepository;
    private final ConstructorDriverRelationDSLRepository relationDSLRepository;

    @Override
    public ConstructorDriverRelation save(ConstructorDriverRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    public ConstructorDriverRelation saveAndFlush(ConstructorDriverRelation relation) {
        return relationRepository.saveAndFlush(relation);
    }

    @Override
    public boolean existsByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        return relationDSLRepository.existsByDriverAndRacingClass(driverId, racingClass);
    }

    @Override
    public boolean existsByDriverAndConstructor(Long driverId, Long constructorId) {
        return relationDSLRepository.existsByDriverAndConstructor(driverId, constructorId);
    }

    @Override
    public ConstructorDriverRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        return relationDSLRepository.findByDriverAndRacingClass(driverId, racingClass);
    }

    @Override
    public List<ConstructorDriverRelation> findAllByDriver(Long driverId) {
        return relationDSLRepository.findAllByDriver(driverId);
    }

    @Override
    public List<ConstructorDriverRelation> findAllByConstructor(Long constructorId) {
        return relationDSLRepository.findAllByConstructor(constructorId);
    }

    @Override
    public void delete(ConstructorDriverRelation relation) {
        relationRepository.delete(relation);
    }
}
