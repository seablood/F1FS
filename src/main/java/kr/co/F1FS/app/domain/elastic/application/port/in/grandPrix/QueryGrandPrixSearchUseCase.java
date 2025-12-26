package kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix;

import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;

public interface QueryGrandPrixSearchUseCase {
    GrandPrixDocument findById(Long id);
}
