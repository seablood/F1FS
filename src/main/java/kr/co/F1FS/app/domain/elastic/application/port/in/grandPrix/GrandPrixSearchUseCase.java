package kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix;

import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GrandPrixSearchUseCase {
    List<ResponseGrandPrixSearchDTO> suggestGrandPrix(String keyword);
    Page<ResponseGrandPrixSearchDTO> searchGrandPrixWithPaging(int page, int size, String condition, String keyword);
}
