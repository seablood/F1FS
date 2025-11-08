package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSearchRepository;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixException;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrandPrixSearchRepoAdapter implements GrandPrixSearchRepoPort {
    private final GrandPrixSearchRepository grandPrixSearchRepository;

    @Override
    public GrandPrixDocument save(GrandPrixDocument document) {
        return grandPrixSearchRepository.save(document);
    }

    @Override
    public GrandPrixDocument findById(Long id) {
        return grandPrixSearchRepository.findById(id)
                .orElseThrow(() -> new GrandPrixException(GrandPrixExceptionType.GRAND_PRIX_NOT_FOUND));
    }
}
