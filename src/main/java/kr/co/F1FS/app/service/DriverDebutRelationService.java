package kr.co.F1FS.app.service;

import kr.co.F1FS.app.dto.ResponseSinceDebutDTO;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.DriverDebutRelation;
import kr.co.F1FS.app.model.SinceDebut;
import kr.co.F1FS.app.repository.DriverDebutRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverDebutRelationService {
    private final DriverDebutRelationRepository debutRelationRepository;

    public void save(Driver driver, SinceDebut sinceDebut){
        DriverDebutRelation debutRelation = DriverDebutRelation.builder()
                .driver(driver)
                .sinceDebut(sinceDebut)
                .build();

        debutRelationRepository.save(debutRelation);
    }

    public DriverDebutRelation findByDriver(Driver driver){
        return debutRelationRepository.findByDriverSinceInfo(driver)
                .orElseThrow(() -> new IllegalArgumentException("해당 드라이버의 연혁을 찾을 수 없습니다."));
    }

    public ResponseSinceDebutDTO getSinceDebut(DriverDebutRelation debutRelation){
        return ResponseSinceDebutDTO.toDto(debutRelation.getSinceDebut());
    }
}
