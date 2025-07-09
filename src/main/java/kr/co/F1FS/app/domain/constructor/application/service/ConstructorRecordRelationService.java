package kr.co.F1FS.app.domain.constructor.application.service;

import kr.co.F1FS.app.domain.constructor.application.mapper.ConstructorRecordRelationMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRecordRelationRepository;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorRecordRelationService implements ConstructorRecordRelationUseCase {
    private final ConstructorRecordRelationMapper relationMapper;
    private final ConstructorRecordRelationRepository relationRepository;

    public void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut){
        ConstructorRecordRelation relation = relationMapper.toConstructorRecordRelation(constructor, currentSeason,
                sinceDebut);

        relationRepository.save(relation);
    }

    @Cacheable(value = "ConstructorRecord", key = "#constructor.id", cacheManager = "redisLongCacheManager")
    public ConstructorRecordRelation findByConstructor(Constructor constructor){
        return relationRepository.findByConstructorInfo(constructor)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_RECORD_ERROR));
    }
}
