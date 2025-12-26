package kr.co.F1FS.app.domain.grandprix.infrastructure.adapter;

import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixJpaPort;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.infrastructure.repository.GrandPrixRepository;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixException;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrandPrixJpaAdapter implements GrandPrixJpaPort {
    private final GrandPrixRepository grandPrixRepository;
    private final GrandPrixMapper grandPrixMapper;

    @Override
    public GrandPrix save(GrandPrix grandPrix) {
        return grandPrixRepository.save(grandPrix);
    }

    @Override
    public GrandPrix saveAndFlush(GrandPrix grandPrix) {
        return grandPrixRepository.saveAndFlush(grandPrix);
    }

    @Override
    public List<GrandPrix> findGrandPrixesBySeason(Integer season) {
        return grandPrixRepository.findGrandPrixesBySeason(season);
    }

    @Override
    public GrandPrix findById(Long id) {
        return grandPrixRepository.findById(id)
                .orElseThrow(() -> new GrandPrixException(GrandPrixExceptionType.GRAND_PRIX_NOT_FOUND));
    }
}
