package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.grandprix.application.port.in.CreateGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixJpaPort;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGrandPrixService implements CreateGrandPrixUseCase {
    private final GrandPrixJpaPort grandPrixJpaPort;
    private final GrandPrixDomainService grandPrixDomainService;

    @Override
    public GrandPrix save(CreateGrandPrixCommand command) {
        GrandPrix grandPrix = grandPrixDomainService.createEntity(command);

        return grandPrixJpaPort.save(grandPrix);
    }
}
