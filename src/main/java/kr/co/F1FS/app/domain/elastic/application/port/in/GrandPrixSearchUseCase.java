package kr.co.F1FS.app.domain.elastic.application.port.in;

import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GrandPrixSearchUseCase {
    GrandPrixDocument save(GrandPrix grandPrix);
    GrandPrixDocument save(GrandPrixDocument document);
    GrandPrixDocument findById(Long id);
    void modify(GrandPrixDocument document, GrandPrix grandPrix);
    List<ResponseGrandPrixSearchDTO> suggestGrandPrix(String keyword);
    Page<ResponseGrandPrixSearchDTO> searchGrandPrixWithPaging(int page, int size, String condition, String keyword);
}
