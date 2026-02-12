package kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix;

import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseSuggestGrandPrixSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GrandPrixSuggestSearchUseCase {
    List<ResponseSuggestGrandPrixSearchDTO> getAutoGrandPrixList(String keyword);
    Page<ResponseSuggestGrandPrixSearchDTO> getGrandPrixList(int page, int size, String condition, String keyword);
}
