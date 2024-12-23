package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.*;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.ConstructorRecordRelation;
import kr.co.F1FS.app.model.CurrentSeason;
import kr.co.F1FS.app.model.SinceDebut;
import kr.co.F1FS.app.repository.ConstructorRepository;
import kr.co.F1FS.app.repository.CurrentSeasonRepository;
import kr.co.F1FS.app.repository.SinceDebutRepository;
import lombok.RequiredArgsConstructor;
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

    public ResponseConstructorDTO findById(Long id){
        Constructor constructor = constructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Constructor를 찾지 못했습니다."));
        ConstructorRecordRelation relation = recordRelationService.findByConstructor(constructor);
        ResponseCurrentSeasonDTO currentSeasonDTO = ResponseCurrentSeasonDTO.toDto(relation.getCurrentSeason());
        ResponseSinceDebutDTO sinceDebutDTO = ResponseSinceDebutDTO.toDto(relation.getSinceDebut());

        return ResponseConstructorDTO.toDto(constructor, getDrivers(constructor), currentSeasonDTO, sinceDebutDTO);
    }

    public List<String> getDrivers(Constructor constructor){
        return constructor.getDrivers().stream()
                .map((relation) -> relation.getDriver().getName())
                .toList();
    }
}
