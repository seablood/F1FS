package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.mapper.admin.AdminConstructorDriverRelationMapper;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorDriverRelationDomainService {
    private final AdminConstructorDriverRelationMapper cdRelationMapper;

    public ConstructorDriverRelation createEntity(Constructor constructor, Driver driver){
        return cdRelationMapper.toConstructorDriverRelation(constructor, driver);
    }

    public void updateConstructor(ConstructorDriverRelation relation, Constructor constructor){
        relation.updateConstructor(constructor);
    }
}
