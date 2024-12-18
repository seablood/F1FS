package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.ConstructorDriverRelation;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.repository.ConstructorDriverRelationRepository;
import kr.co.F1FS.app.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorDriverRelationService {
    private final DriverRepository driverRepository;
    private final ConstructorDriverRelationRepository relationRepository;

    public void save(Constructor constructor, Driver driver){
        ConstructorDriverRelation relation = ConstructorDriverRelation.builder()
                .constructor(constructor)
                .driver(driver)
                .build();
        relationRepository.save(relation);
    }

    @Transactional
    public void delete(Long driverId){
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("driver를 찾지 못했습니다."));
        ConstructorDriverRelation relation = relationRepository.findConstructorDriverRelationByDriver(driver);

        driver.updateTeam("FA");

        relationRepository.delete(relation);
    }
}
