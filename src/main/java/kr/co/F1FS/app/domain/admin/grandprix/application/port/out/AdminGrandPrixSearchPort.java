package kr.co.F1FS.app.domain.admin.grandprix.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;

public interface AdminGrandPrixSearchPort {
    void save(GrandPrixDocument grandPrixDocument);
}
