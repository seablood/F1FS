package kr.co.F1FS.app.domain.constructor.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.mapper.constructor.ConstructorMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.ConstructorQueryAggregatorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.QueryConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.domain.team.application.port.in.admin.QueryCDRelationUseCase;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorQueryAggregatorService implements ConstructorQueryAggregatorUseCase {
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final QueryConstructorRecordRelationUseCase queryConstructorRecordRelationUseCase;
    private final QueryCDRelationUseCase queryCDRelationUseCase;
    private final ConstructorMapper constructorMapper;
    private final RecordMapper recordMapper;

    @Override
    public ResponseConstructorDTO findByIdForDTO(Long id) {
        Constructor constructor = queryConstructorUseCase.findById(id);
        ConstructorRecordRelation relation = queryConstructorRecordRelationUseCase.findByConstructor(constructor);
        ResponseCurrentSeasonDTO currentSeasonDTO = recordMapper.toResponseCurrentSeasonDTO(relation.getCurrentSeason());
        ResponseSinceDebutDTO sinceDebutDTO = recordMapper.toResponseSinceDebutDTO(relation.getSinceDebut());

        return constructorMapper.toResponseConstructorDTO(constructor, getDrivers(constructor), currentSeasonDTO,
                sinceDebutDTO);
    }

    public List<String> getDrivers(Constructor constructor){
        return queryCDRelationUseCase.findConstructorDriverRelationByConstructor(constructor).stream()
                .map((relation) -> relation.getDriver().getName())
                .toList();
    }
}
