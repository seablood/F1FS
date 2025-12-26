package kr.co.F1FS.app.domain.constructor.application.service.record;

import kr.co.F1FS.app.domain.constructor.application.mapper.record.ConstructorRecordRelationMapper;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorRecordRelationDomainService {
    private final ConstructorRecordRelationMapper relationMapper;

    public ConstructorRecordRelation createEntity(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut){
        return relationMapper.toConstructorRecordRelation(constructor, currentSeason, sinceDebut);
    }

    public void updateEntryClassSeason(ConstructorRecordRelation relation, boolean entryClassSeason){
        relation.updateEntryClassSeason(entryClassSeason);
    }
}
