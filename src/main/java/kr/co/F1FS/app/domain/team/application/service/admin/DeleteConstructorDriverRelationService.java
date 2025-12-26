package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.port.in.admin.DeleteConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteConstructorDriverRelationService implements DeleteConstructorDriverRelationUseCase {
    private final CDRelationJpaPort cdRelationJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(Driver driver, String option) {
        ConstructorDriverRelation relation;
        cacheEvictUtil.evictCachingDriver(driver);

        switch (option){
            case "currentTeam" :
                relation = cdRelationJpaPort.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, driver.getRacingClass()
                );
                cacheEvictUtil.evictCachingConstructor(relation.getConstructor());
                cdRelationJpaPort.delete(relation);
                break;

            case "F1", "F2", "F3", "F1_ACADEMY", "RESERVE" :
                RacingClass racingClass = RacingClass.valueOf(option);
                relation = cdRelationJpaPort.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, racingClass
                );
                cacheEvictUtil.evictCachingConstructor(relation.getConstructor());
                cdRelationJpaPort.delete(relation);
                break;

            case "all" :
                List<ConstructorDriverRelation> list = cdRelationJpaPort.findConstructorDriverRelationByDriver(driver);
                list.stream().forEach(relation1 -> {
                    cacheEvictUtil.evictCachingConstructor(relation1.getConstructor());
                    cdRelationJpaPort.delete(relation1);
                });
                break;

            default:
                throw new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR);
        }
    }
}
