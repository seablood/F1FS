package kr.co.F1FS.app.domain.constructor.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.port.in.record.CreateConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.CreateConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.constructor.ConstructorJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.CreateConstructorDTO;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.CreateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.CreateSinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateConstructorService implements CreateConstructorUseCase {
    private final ConstructorJpaPort constructorJpaPort;
    private final ConstructorDomainService constructorDomainService;
    private final CreateCurrentSeasonUseCase createCurrentSeasonUseCase;
    private final CreateSinceDebutUseCase createSinceDebutUseCase;
    private final CreateConstructorRecordRelationUseCase createConstructorRecordRelationUseCase;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;

    @Override
    public Constructor save(CreateConstructorDTO constructorDTO, CreateCurrentSeasonDTO currentSeasonDTO, CreateSinceDebutDTO sinceDebutDTO) {
        Constructor constructor = constructorJpaPort.save(constructorDomainService.createEntity(constructorDTO));
        CurrentSeason currentSeason = createCurrentSeasonUseCase.save(currentSeasonDTO);
        SinceDebut sinceDebut = createSinceDebutUseCase.save(sinceDebutDTO);

        createConstructorRecordRelationUseCase.save(constructor, currentSeason, sinceDebut);
        saveSuggestKeyword(constructor);

        return constructor;
    }

    public void saveSuggestKeyword(Constructor constructor){
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(constructor.getName());
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(constructor.getEngName());
    }
}
