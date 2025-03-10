package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.ConstructorDriverRelation;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.repository.ConstructorDriverRelationRepository;
import kr.co.F1FS.app.repository.ConstructorRepository;
import kr.co.F1FS.app.repository.DriverRepository;
import kr.co.F1FS.app.util.constructor.ConstructorException;
import kr.co.F1FS.app.util.constructor.ConstructorExceptionType;
import kr.co.F1FS.app.util.driver.DriverException;
import kr.co.F1FS.app.util.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorDriverRelationService {
    private final ConstructorRepository constructorRepository;
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
    public void transfer(Integer number, String constructorName){
        Driver driver = driverRepository.findByNumber(number)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
        Constructor constructor = constructorRepository.findByName(constructorName)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
        ConstructorDriverRelation relation = ConstructorDriverRelation.builder()
                .constructor(constructor)
                .driver(driver)
                .build();
        driver.updateTeam(constructorName);

        relationRepository.save(relation);
    }

    @Transactional
    public void delete(Long driverId){
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
        ConstructorDriverRelation relation = relationRepository.findConstructorDriverRelationByDriver(driver);

        driver.updateTeam("FA");

        relationRepository.delete(relation);
    }
}
