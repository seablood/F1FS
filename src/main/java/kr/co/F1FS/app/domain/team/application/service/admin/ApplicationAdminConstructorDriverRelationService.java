package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.UpdateDriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.port.in.admin.*;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminConstructorDriverRelationService implements ConstructorDriverRelationUseCase {
    private final CreateConstructorDriverRelationUseCase createConstructorDriverRelationUseCase;
    private final UpdateConstructorDriverRelationUseCase updateConstructorDriverRelationUseCase;
    private final DeleteConstructorDriverRelationUseCase deleteConstructorDriverRelationUseCase;
    private final QueryCDRelationUseCase queryCDRelationUseCase;
    private final CheckConstructorDriverRelationUseCase checkConstructorDriverRelationUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final QueryDriverUseCase queryDriverUseCase;
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public void transfer(Integer number, String constructorName){
        Driver driver = queryDriverUseCase.findByNumber(number);
        Constructor constructor = queryConstructorUseCase.findByName(constructorName);
        cacheEvictUtil.evictCachingDriver(driver);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(checkConstructorDriverRelationUseCase.existsConstructorDriverRelationByDriverAndRacingClass(driver, constructor.getRacingClass())){
            if (!checkConstructorDriverRelationUseCase.existsConstructorDriverRelationByDriverAndConstructor(driver, constructor)) {
                ConstructorDriverRelation relation = queryCDRelationUseCase.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, constructor.getRacingClass()
                );
                updateConstructorDriverRelationUseCase.updateConstructor(relation, constructor);
            }
            updateDriverUseCase.transfer(driver, constructor);
        } else {
            createConstructorDriverRelationUseCase.save(constructor, driver);
            updateDriverUseCase.transfer(driver, constructor);
        }
    }

    @Override
    @Transactional
    public void delete(Long id, String option) {
        Driver driver = queryDriverUseCase.findById(id);

        deleteConstructorDriverRelationUseCase.delete(driver, option);
    }
}
