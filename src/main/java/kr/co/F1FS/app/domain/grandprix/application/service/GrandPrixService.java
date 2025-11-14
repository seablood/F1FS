package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.circuit.application.port.in.CircuitUseCase;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.in.GrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixJpaPort;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrandPrixService implements GrandPrixUseCase {
    private final GrandPrixJpaPort grandPrixJpaPort;
    private final CircuitUseCase circuitUseCase;
    private final GrandPrixMapper grandPrixMapper;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public GrandPrix createGrandPrix(CreateGrandPrixCommand command) {
        GrandPrix grandPrix = grandPrixMapper.toGrandPrix(command);
        validationService.checkValid(grandPrix);

        return grandPrix;
    }

    @Override
    public GrandPrix save(GrandPrix grandPrix) {
        return grandPrixJpaPort.save(grandPrix);
    }

    @Override
    public GrandPrix saveAndFlush(GrandPrix grandPrix) {
        return grandPrixJpaPort.saveAndFlush(grandPrix);
    }

    @Override
    @Cacheable(value = "GrandPrixList", key = "#season", cacheManager = "redisLongCacheManager")
    public List<SimpleResponseGrandPrixDTO> findAll(Integer season){
        return grandPrixJpaPort.findGrandPrixesBySeason(season);
    }

    @Override
    public GrandPrix findByIdNotDTONotCache(Long id) {
        return grandPrixJpaPort.findById(id);
    }

    @Override
    @Cacheable(value = "GrandPrixDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseGrandPrixDTO getGrandPrixById(Long id) {
        GrandPrix grandPrix = grandPrixJpaPort.findById(id);
        Circuit circuit = circuitUseCase.findByIdNotDTONotCache(grandPrix.getCircuitId());

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitUseCase.toSimpleResponseCircuitDTO(circuit));
    }

    @Override
    public GrandPrix modify(ModifyGrandPrixCommand command, GrandPrix grandPrix) {
        cacheEvictUtil.evictCachingGrandPrix(grandPrix);
        grandPrix.modify(command);
        validationService.checkValid(grandPrix);

        return grandPrix;
    }

    @Override
    public void setFirstPractice(GrandPrix grandPrix, Long firstPractice) {
        grandPrix.setFirstPractice(firstPractice);
    }

    @Override
    public void setSecondPractice(GrandPrix grandPrix, Long secondPractice) {
        grandPrix.setSecondPractice(secondPractice);
    }

    @Override
    public void setThirdPractice(GrandPrix grandPrix, Long thirdPractice) {
        grandPrix.setThirdPractice(thirdPractice);
    }

    @Override
    public void setSprintQualifying(GrandPrix grandPrix, Long sprintQualifying) {
        grandPrix.setSprintQualifying(sprintQualifying);
    }

    @Override
    public void setQualifying(GrandPrix grandPrix, Long qualifying) {
        grandPrix.setQualifying(qualifying);
    }

    @Override
    public void setSprint(GrandPrix grandPrix, Long sprint) {
        grandPrix.setSprint(sprint);
    }

    @Override
    public void setRace(GrandPrix grandPrix, Long race) {
        grandPrix.setRace(race);
    }

    @Override
    public ResponseGrandPrixDTO toResponseGrandPrixDTO(GrandPrix grandPrix, SimpleResponseCircuitDTO dto) {
        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, dto);
    }
}
