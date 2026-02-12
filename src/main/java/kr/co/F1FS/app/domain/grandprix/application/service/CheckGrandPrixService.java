package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.grandprix.application.port.in.CheckGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckGrandPrixService implements CheckGrandPrixUseCase {
    private final GrandPrixJpaPort grandPrixJpaPort;

    @Override
    public boolean existsGrandPrixByNameAndEngName(String name, String engName) {
        return grandPrixJpaPort.existsGrandPrixByNameAndEngName(name, engName);
    }
}
