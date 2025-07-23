package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.in.GrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixCircuitPort;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.infrastructure.repository.GrandPrixRepository;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.SessionType;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixException;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrandPrixService implements GrandPrixUseCase {
    private final GrandPrixRepository grandPrixRepository;
    private final GrandPrixCircuitPort circuitPort;
    private final GrandPrixMapper grandPrixMapper;
    private final CircuitMapper circuitMapper;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public GrandPrix createGrandPrix(CreateGrandPrixCommand command) {
        GrandPrix grandPrix = grandPrixMapper.toGrandPrix(command);
        validationService.checkValid(grandPrix);

        return grandPrix;
    }

    @Override
    public Page<SimpleResponseGrandPrixDTO> findAll(int page, int size, Integer season){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "firstPracticeTime"));

        return grandPrixRepository.findGrandPrixesBySeason(season, pageable)
                .map(grandPrix -> grandPrixMapper.toSimpleResponseGrandPrixDTO(grandPrix));
    }

    @Override
    @Cacheable(value = "GrandPrixDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseGrandPrixDTO getGrandPrixById(Long id) {
        GrandPrix grandPrix = grandPrixRepository.findById(id)
                .orElseThrow(() -> new GrandPrixException(GrandPrixExceptionType.GRAND_PRIX_NOT_FOUND));
        Circuit circuit = circuitPort.getCircuitByIdNotDTO(grandPrix.getCircuitId());

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitMapper.toSimpleResponseCircuitDTO(circuit));
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
}
