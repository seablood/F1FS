package kr.co.F1FS.app.service;

import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.ConstructorRecordRelation;
import kr.co.F1FS.app.model.CurrentSeason;
import kr.co.F1FS.app.model.SinceDebut;
import kr.co.F1FS.app.repository.ConstructorRecordRelationRepository;
import lombok.RequiredArgsConstructor;
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

    public ConstructorRecordRelation findByConstructor(Constructor constructor){
        return relationRepository.findByConstructorInfo(constructor)
                .orElseThrow(() -> new IllegalArgumentException("컨스트럭터 정보 오류"));
    }
}