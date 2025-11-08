package kr.co.F1FS.app.domain.constructor.application.service;

import kr.co.F1FS.app.domain.constructor.application.mapper.ConstructorMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.ConstructorJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ModifyConstructorCommand;
import kr.co.F1FS.app.domain.record.application.port.in.CurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.SinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationUseCase;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import kr.co.F1FS.app.domain.constructor.presentation.dto.CreateConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorService implements ConstructorUseCase {
    private final CurrentSeasonUseCase currentSeasonUseCase;
    private final SinceDebutUseCase sinceDebutUseCase;
    private final ConstructorDriverRelationUseCase relationUseCase;
    private final ConstructorMapper constructorMapper;
    private final ConstructorRecordRelationService recordRelationService;
    private final ConstructorJpaPort constructorJpaPort;

    @Transactional
    public Constructor save(CreateConstructorDTO constructorDTO, CreateCurrentSeasonDTO currentSeasonDTO,
                            CreateSinceDebutDTO sinceDebutDTO){
        Constructor constructor = CreateConstructorDTO.toEntity(constructorDTO);
        CurrentSeason currentSeason = currentSeasonUseCase.toCurrentSeason(currentSeasonDTO);
        SinceDebut sinceDebut = sinceDebutUseCase.toSinceDebut(sinceDebutDTO);

        recordRelationService.save(constructor, currentSeason, sinceDebut);

        currentSeasonUseCase.save(currentSeason);
        sinceDebutUseCase.save(sinceDebut);

        return constructorJpaPort.save(constructor);
    }

    @Override
    public Constructor save(Constructor constructor) {
        return constructorJpaPort.save(constructor);
    }

    @Override
    public Constructor saveAndFlush(Constructor constructor) {
        return constructorJpaPort.saveAndFlush(constructor);
    }

    public Page<SimpleResponseConstructorDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return constructorJpaPort.findAll(pageable);
    }

    @Cacheable(value = "ConstructorDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseConstructorDTO findById(Long id){
        Constructor constructor = constructorJpaPort.findById(id);
        ConstructorRecordRelation relation = recordRelationService.findByConstructor(constructor);
        ResponseCurrentSeasonDTO currentSeasonDTO = currentSeasonUseCase.toResponseCurrentSeasonDTO(relation.getCurrentSeason());
        ResponseSinceDebutDTO sinceDebutDTO = sinceDebutUseCase.toResponseSinceDebutDTO(relation.getSinceDebut());

        return constructorMapper.toResponseConstructorDTO(constructor, getDrivers(constructor), currentSeasonDTO,
                sinceDebutDTO);
    }

    @Cacheable(value = "Constructor", key = "#id", cacheManager = "redisLongCacheManager")
    public Constructor findByIdNotDTO(Long id){
        return constructorJpaPort.findById(id);
    }

    @Override
    public void modify(Constructor constructor, ModifyConstructorCommand command){
        constructor.modify(command);
    }

    @Override
    public void updateRecordForRace(Constructor constructor, int position, int points, boolean isFastestLap){
        ConstructorRecordRelation relation = recordRelationService.findByConstructor(constructor);

        if(!relation.isEntryClassSeason()) relation.updateEntryClassSeason(true);

        recordRelationService.updateRecordForRace(relation, position, points, isFastestLap);
    }

    @Override
    public void updateRecordForQualifying(Constructor constructor, int position){
        ConstructorRecordRelation relation = recordRelationService.findByConstructor(constructor);

        if(!relation.isEntryClassSeason()) relation.updateEntryClassSeason(true);

        recordRelationService.updateRecordForQualifying(relation, position);
    }

    public void increaseFollower(Constructor constructor){
        constructor.increaseFollower();
    }

    public void decreaseFollower(Constructor constructor){
        constructor.decreaseFollower();
    }

    public List<String> getDrivers(Constructor constructor){
        return relationUseCase.findConstructorDriverRelationByConstructor(constructor).stream()
                .map((relation) -> relation.getDriver().getName())
                .toList();
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "korName"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "korName"));
            case "racingClass" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "racingClass"));
            default:
                throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
        }
    }
}
