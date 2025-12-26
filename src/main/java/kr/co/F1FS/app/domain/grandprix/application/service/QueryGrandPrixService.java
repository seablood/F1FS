package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.in.QueryGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixJpaPort;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryGrandPrixService implements QueryGrandPrixUseCase {
    private final GrandPrixJpaPort grandPrixJpaPort;
    private final GrandPrixMapper grandPrixMapper;

    @Override
    public GrandPrix findById(Long id) {
        return grandPrixJpaPort.findById(id);
    }

    @Override
    public List<SimpleResponseGrandPrixDTO> findAll(Integer season) {
        return grandPrixJpaPort.findGrandPrixesBySeason(season).stream()
                .map(grandPrix -> grandPrixMapper.toSimpleResponseGrandPrixDTO(grandPrix))
                .toList();
    }
}
