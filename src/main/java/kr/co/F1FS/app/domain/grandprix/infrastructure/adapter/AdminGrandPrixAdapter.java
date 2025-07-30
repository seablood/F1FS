package kr.co.F1FS.app.domain.grandprix.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.grandprix.application.port.out.AdminGrandPrixPort;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.infrastructure.repository.GrandPrixRepository;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixException;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminGrandPrixAdapter implements AdminGrandPrixPort {
    private final GrandPrixRepository grandPrixRepository;

    @Override
    public GrandPrix save(GrandPrix grandPrix) {
        return grandPrixRepository.save(grandPrix);
    }

    @Override
    public void saveAndFlush(GrandPrix grandPrix) {
        grandPrixRepository.saveAndFlush(grandPrix);
    }

    @Override
    public GrandPrix getGrandPrixById(Long id) {
        return grandPrixRepository.findById(id)
                .orElseThrow(() -> new GrandPrixException(GrandPrixExceptionType.GRAND_PRIX_NOT_FOUND));
    }
}
