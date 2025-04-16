package kr.co.F1FS.app.application.constructor;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.model.rdb.Constructor;
import kr.co.F1FS.app.domain.model.rdb.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.model.rdb.CurrentSeason;
import kr.co.F1FS.app.domain.model.rdb.SinceDebut;
import kr.co.F1FS.app.domain.repository.rdb.constructor.ConstructorRepository;
import kr.co.F1FS.app.domain.repository.rdb.record.CurrentSeasonRepository;
import kr.co.F1FS.app.domain.repository.rdb.record.SinceDebutRepository;
import kr.co.F1FS.app.presentation.constructor.dto.CombinedConstructorRequest;
import kr.co.F1FS.app.presentation.constructor.dto.CreateConstructorDTO;
import kr.co.F1FS.app.presentation.constructor.dto.ResponseConstructorDTO;
import kr.co.F1FS.app.presentation.constructor.dto.ResponseSimpleConstructorDTO;
import kr.co.F1FS.app.presentation.record.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.presentation.record.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.presentation.record.dto.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.presentation.record.dto.ResponseSinceDebutDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorService {
    private final ConstructorRecordRelationService recordRelationService;
    private final ConstructorRepository constructorRepository;
    private final CurrentSeasonRepository currentSeasonRepository;
    private final SinceDebutRepository sinceDebutRepository;

    @Transactional
    public Constructor save(CombinedConstructorRequest request){
        Constructor constructor = CreateConstructorDTO.toEntity(request.getConstructorDTO());
        CurrentSeason currentSeason = CreateCurrentSeasonDTO.toEntity(request.getCurrentSeasonDTO());
        SinceDebut sinceDebut = CreateSinceDebutDTO.toEntity(request.getSinceDebutDTO());

        recordRelationService.save(constructor, currentSeason, sinceDebut);

        currentSeasonRepository.save(currentSeason);
        sinceDebutRepository.save(sinceDebut);
        return constructorRepository.save(constructor);
    }

    @Cacheable(value = "ConstructorDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseConstructorDTO findById(Long id){
        Constructor constructor = constructorRepository.findById(id)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
        ConstructorRecordRelation relation = recordRelationService.findByConstructor(constructor);
        ResponseCurrentSeasonDTO currentSeasonDTO = ResponseCurrentSeasonDTO.toDto(relation.getCurrentSeason());
        ResponseSinceDebutDTO sinceDebutDTO = ResponseSinceDebutDTO.toDto(relation.getSinceDebut());

        return ResponseConstructorDTO.toDto(constructor, getDrivers(constructor), currentSeasonDTO, sinceDebutDTO);
    }

    public Page<ResponseSimpleConstructorDTO> findByNameList(String search, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return constructorRepository.findAllByNameContainsIgnoreCaseOrEngNameContainsIgnoreCase(search, search, pageable)
                .map(constructor -> ResponseSimpleConstructorDTO.toDto(constructor));
    }

    @Cacheable(value = "Constructor", key = "#id", cacheManager = "redisLongCacheManager")
    public Constructor findByIdNotDTO(Long id){
        return constructorRepository.findById(id)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
    }

    public Page<ResponseSimpleConstructorDTO> findByRacingClass(String findClass, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        RacingClass racingClass = RacingClass.valueOf(findClass);

        return constructorRepository.findAllByRacingClass(racingClass, pageable)
                .map(constructor -> ResponseSimpleConstructorDTO.toDto(constructor));
    }

    @Cacheable(value = "ConDriverList", key = "#constructor.id", cacheManager = "redisLongCacheManager")
    public List<String> getDrivers(Constructor constructor){
        return constructor.getDrivers().stream()
                .map((relation) -> relation.getDriver().getName())
                .toList();
    }
}
