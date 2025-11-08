package kr.co.F1FS.app.domain.record.infrastructure.adapter;

import kr.co.F1FS.app.domain.record.application.port.out.SinceDebutJpaPort;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.infrastructure.repository.SinceDebutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SinceDebutJpaAdapter implements SinceDebutJpaPort {
    private final SinceDebutRepository sinceDebutRepository;

    @Override
    public SinceDebut save(SinceDebut sinceDebut) {
        return sinceDebutRepository.save(sinceDebut);
    }

    @Override
    public SinceDebut saveAndFlush(SinceDebut sinceDebut) {
        return sinceDebutRepository.saveAndFlush(sinceDebut);
    }
}
