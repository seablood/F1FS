package kr.co.F1FS.app.service;

import kr.co.F1FS.app.dto.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.dto.ResponseSinceDebutDTO;
import kr.co.F1FS.app.model.CurrentSeason;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.DriverRecordRelation;
import kr.co.F1FS.app.model.SinceDebut;
import kr.co.F1FS.app.repository.DriverRecordRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRecordRelationService {
    private final DriverRecordRelationRepository relationRepository;

    public void save(Driver driver, CurrentSeason currentSeason, SinceDebut sinceDebut){
        DriverRecordRelation relation = DriverRecordRelation.builder()
                .driver(driver)
                .currentSeason(currentSeason)
                .sinceDebut(sinceDebut)
                .build();

        relationRepository.save(relation);
    }

    public DriverRecordRelation findByDriver(Driver driver){
        return relationRepository.findByDriverInfo(driver)
                .orElseThrow(() -> new IllegalArgumentException("해당 드라이버의 정보가 존재하지 않습니다."));
    }

    public ResponseCurrentSeasonDTO getCurrentSeason(DriverRecordRelation recordRelation){
        return ResponseCurrentSeasonDTO.toDto(recordRelation.getCurrentSeason());
    }

    public ResponseSinceDebutDTO getSinceDebut(DriverRecordRelation recordRelation){
        return ResponseSinceDebutDTO.toDto(recordRelation.getSinceDebut());
    }
}
