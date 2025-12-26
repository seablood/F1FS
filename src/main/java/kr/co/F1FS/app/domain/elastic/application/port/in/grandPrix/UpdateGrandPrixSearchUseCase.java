package kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix;

import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;

public interface UpdateGrandPrixSearchUseCase {
    void modify(GrandPrixDocument document, GrandPrix grandPrix);
}
