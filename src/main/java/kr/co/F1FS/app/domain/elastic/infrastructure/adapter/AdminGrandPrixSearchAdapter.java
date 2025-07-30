package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.grandprix.application.port.out.AdminGrandPrixSearchPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminGrandPrixSearchAdapter implements AdminGrandPrixSearchPort {
    private final GrandPrixSearchRepository grandPrixSearchRepository;

    @Override
    public void save(GrandPrixDocument grandPrixDocument) {
        grandPrixSearchRepository.save(grandPrixDocument);
    }
}
