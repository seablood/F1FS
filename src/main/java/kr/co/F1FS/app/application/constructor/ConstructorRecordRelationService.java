package kr.co.F1FS.app.application.constructor;

import kr.co.F1FS.app.domain.model.rdb.Constructor;
import kr.co.F1FS.app.domain.model.rdb.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.model.rdb.CurrentSeason;
import kr.co.F1FS.app.domain.model.rdb.SinceDebut;
import kr.co.F1FS.app.domain.repository.rdb.constructor.ConstructorRecordRelationRepository;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorRecordRelationService {
    private final ConstructorRecordRelationRepository relationRepository;

    public void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut){
        ConstructorRecordRelation relation = ConstructorRecordRelation.builder()
                .constructor(constructor)
                .currentSeason(currentSeason)
                .sinceDebut(sinceDebut)
                .build();

        relationRepository.save(relation);
    }

    @Cacheable(value = "ConstructorRecord", key = "#constructor.id", cacheManager = "redisLongCacheManager")
    public ConstructorRecordRelation findByConstructor(Constructor constructor){
        return relationRepository.findByConstructorInfo(constructor)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_RECORD_ERROR));
    }
}
