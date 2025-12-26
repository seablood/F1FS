package kr.co.F1FS.app.domain.team.application.mapper.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import org.springframework.stereotype.Component;

@Component
public class AdminConstructorDriverRelationMapper {
    public ConstructorDriverRelation toConstructorDriverRelation(Constructor constructor, Driver driver){
        return ConstructorDriverRelation.builder()
                .constructor(constructor)
                .driver(driver)
                .racingClass(constructor.getRacingClass())
                .build();
    }
}
