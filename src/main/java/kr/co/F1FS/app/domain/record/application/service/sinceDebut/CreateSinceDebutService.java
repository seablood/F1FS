package kr.co.F1FS.app.domain.record.application.service.sinceDebut;

import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.CreateSinceDebutUseCase;
import kr.co.F1FS.app.domain.record.application.port.out.sinceDebut.SinceDebutJpaPort;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSinceDebutService implements CreateSinceDebutUseCase {
    private final SinceDebutJpaPort sinceDebutJpaPort;
    private final SinceDebutDomainService sinceDebutDomainService;

    @Override
    public SinceDebut save(CreateSinceDebutDTO sinceDebutDTO) {
        SinceDebut sinceDebut = sinceDebutDomainService.createEntity(sinceDebutDTO);

        return sinceDebutJpaPort.save(sinceDebut);
    }

    @Override
    public SinceDebut save() {
        SinceDebut sinceDebut = sinceDebutDomainService.createEntity();

        return sinceDebutJpaPort.save(sinceDebut);
    }
}
